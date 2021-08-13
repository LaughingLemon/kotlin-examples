package org.lemo.kotlin.reactive

import io.reactivex.Observable

class FakeDb {

    fun getUsers() : Observable<Map.Entry<Int, String>> {
        return Observable.fromIterable(mapOf(
            1 to "Andrew",
            2 to "Alison",
            3 to "Justine"
        ).entries)
    }

    fun getPointsPerUser(userId: Int) : Observable<Int> {
        val pointsMap = mapOf(1 to 20, 2 to 86, 3 to 17)
        return Observable.just(pointsMap[userId])
    }

}

fun main(args: Array<String>) {
    val db = FakeDb()

    db.getUsers().map { user -> user.value }.subscribe { println(it) }
    db.getUsers().flatMap { user ->
        db.getPointsPerUser(user.key)
            .zipWith(Observable.just(user.value), {
                points, username -> "$username has $points"
            })
    }.subscribe { println(it) }
}