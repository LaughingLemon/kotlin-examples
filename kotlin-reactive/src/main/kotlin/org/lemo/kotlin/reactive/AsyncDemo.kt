package org.lemo.kotlin.reactive

import io.reactivex.rxkotlin.toFlowable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    listOf("John", "Andrew", "Paul").toFlowable()
        .doOnNext { println("Transmit, $it, thread: ${Thread.currentThread().name}")}
        .subscribeOn(Schedulers.newThread())
        .blockingSubscribe( { println("Receive, $it, thread: ${Thread.currentThread().name}")} )
}