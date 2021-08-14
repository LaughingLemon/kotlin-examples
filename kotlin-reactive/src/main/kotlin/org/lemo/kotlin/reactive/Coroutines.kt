package org.lemo.kotlin.reactive

import io.reactivex.rxkotlin.blockingSubscribeBy
import kotlinx.coroutines.rx2.rxFlowable

fun main(args: Array<String>) {
    rxFlowable {
        listOf("John", "Paul", "Mark", "Andrew", "George", "Ringo")
            .forEach { send(it) }
    }.map {
        it.uppercase()
    }.blockingSubscribeBy(
        onNext = {
            println(it)
        }
    )
}
