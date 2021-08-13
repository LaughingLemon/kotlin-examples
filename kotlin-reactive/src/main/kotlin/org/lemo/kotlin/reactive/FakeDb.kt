package org.lemo.kotlin.reactive

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.toFlowable

class FakeDb: Db {
    private val userMap = mutableMapOf(
        1 to User(1, "Andrew"),
        2 to User(2, "Alison"),
        3 to User(3, "Justine")
    )

    private val pointsMap = mutableMapOf(1 to 20, 2 to 86, 3 to 17)

    override fun getAllUsers(): Flowable<User> = userMap.values.toFlowable()

    override fun getUserById(id: Int): Maybe<User> =
        getAllUsers().filter { it.id == id }.firstElement()

    override fun getPointsForUserId(id: Int): Single<Int> =
        if (pointsMap.containsKey(id))
            Single.just(pointsMap[id])
        else
            Single.error { UserNotFound("Record $id is not found") }

    override fun addUser(user: User): Completable =
        Completable.fromAction { userMap.put(user.id, user) }

}

interface Db {
    fun getAllUsers(): Flowable<User>
    fun getUserById(id: Int): Maybe<User>
    fun getPointsForUserId(id: Int): Single<Int>
    fun addUser(user: User): Completable
}

data class User(val id: Int, val name: String)
class UserNotFound(message: String?): Exception(message)

fun main(args: Array<String>) {
    val db = FakeDb()

    db.getAllUsers()
        .flatMapMaybe { user -> db.getUserById(user.id) }
        .subscribe { println(it) }

    db.getAllUsers()
        .flatMapSingle { Single.just(it).zipWith(db.getPointsForUserId(it.id), {
            user, points -> "${user.name} has $points"
        } ) }
        .subscribe { println(it) }
}