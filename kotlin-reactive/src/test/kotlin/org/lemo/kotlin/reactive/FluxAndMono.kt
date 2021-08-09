package org.lemo.kotlin.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

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

}