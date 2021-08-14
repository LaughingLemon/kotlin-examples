package org.lemo.kotlin.reactive

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toFlowable
import kotlin.random.Random

fun main(args: Array<String>) {
    listOf("John", "Andrew", "Paul").toFlowable()
        .doOnNext {
            if (Random.nextBoolean())
                throw (IllegalArgumentException("Invalid"))
            else
                it
        }
        .onErrorReturn { "Something" }
        .subscribeBy(
            onNext = {
                println(it)
            },
            onError = {
                println(it)
            },
            onComplete = {
                println("Completed")
            }
        )
}