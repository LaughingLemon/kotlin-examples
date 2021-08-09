package org.lemo.kotlin.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicInteger

class FluxAndMono {

    @Test
    fun `flux with one element`() {
        //given
        val intFlux = Flux.fromIterable(intArrayOf(1).toList())

        //when
        val map = intFlux.map { v -> v * 100 }
        val singleValue = map.single()
        //then
        StepVerifier.create(map)
            .expectNext(100)
            .expectComplete()
            .verify()

        assertThat(singleValue.block()).isNotNull.isEqualTo(100)
    }

    @Test
    fun `Just Mono`() {
        //given
        val intMono = Mono.just(1)

        //when
        val map = intMono.map { v -> v * 100 }
        //then
        StepVerifier.create(map)
            .expectNext(100)
            .expectComplete()
            .verify()
    }

    @Test
    fun `Flux from two monos`() {
        //given
        val intMono1 = Mono.just(1)
        val intMono2 = Mono.just(2)

        //when
        val intFlux = intMono1.concatWith(intMono2)
        val map = intFlux.map { v -> v * 100 }
        //then
        StepVerifier.create(map)
            .expectNext(100)
            .expectNext(200)
            .expectComplete()
            .verify()
    }

    @Test
    fun `enploying backpressure`() {
        //given
        val overflow = AtomicInteger()
        val backPressureFlux = Flux.range(1, 100)
            .hide()
            .log()
            .onBackpressureBuffer(3, overflow::set) //buffer three elements, then set the overflow value
        //when
        //then
        StepVerifier.create(backPressureFlux, 0)
            .expectSubscription()
            .thenRequest(1)
            .expectNext(1)
            .thenAwait()
            .thenCancel()
            .verify()
        //the request consumes the first one "1", leaving three more
        //in the buffer, "2", "3", "4", which means "5" is in the overflow
        assertThat(overflow.get()).isEqualTo(5)
    }
}