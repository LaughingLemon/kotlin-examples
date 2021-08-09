package org.lemo.kotlin.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Supplier

class BasicTests {
    val logger = LoggerFactory.getLogger(BasicTests::class.java)

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
}