package org.lemo.kotlin.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration
import java.util.function.Supplier

class BasicTests {
    val logger: Logger = LoggerFactory.getLogger(BasicTests::class.java)

    @Test
    fun `Range of ints to strings`() {
        val res = Flux.range(0, 100)
            .map { v -> action(v) }
            .doOnError { e -> fallbackAction(e) }
            .map { v -> executeAsyncCall(v) }
            .collectList()
            .block()

        assertThat(res).hasSize(100).contains("1", "53")
    }

    private fun executeAsyncCall(v: Supplier<String>?): String = v?.get() ?: ""

    private fun fallbackAction(e: Throwable?) {
        logger.error("This has failed", e)
    }

    private fun action(v: Int?): Supplier<String> = Supplier { v?.toString() ?: "" }

    @Test
    fun `Use step verifier on Int Range`() {
        val evenNums = Flux.range(0, 10).filter { x -> x % 2 == 0 }
        val oddNums = Flux.range(0, 10).filter { x -> x % 2 > 0 }

        val fluxOfInts = Flux.concat(evenNums, oddNums)

        StepVerifier.create(fluxOfInts)
            .expectNext(0, 2, 4, 6, 8)
            .expectNext(1, 3, 5, 7, 9)
            .expectComplete()
            .verify()
    }

    @Test
    fun `Use step verifier on Delayed Int Range merge`() {
        val evenNums = Flux.range(0, 10).filter { x -> x % 2 == 0 }
        val oddNums = Flux.range(0, 10).filter { x -> x % 2 > 0 }

        val fluxOfInts = Flux.merge(
            evenNums.delayElements(Duration.ofMillis(500L)),
            oddNums.delayElements(Duration.ofMillis(300L))
        )

        StepVerifier.create(fluxOfInts)
            .expectNext(1)
            .expectNext(0)
            .expectNext(3, 5)
            .expectNext(2)
            .expectNext(7)
            .expectNext(4)
            .expectNext(9)
            .expectNext(6, 8)
            .expectComplete()
            .verify()

    }
}