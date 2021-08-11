package org.lemo.kotlin.reactive.routing

import org.junit.jupiter.api.Test
import org.lemo.kotlin.reactive.model.DeliveryItem
import org.lemo.kotlin.reactive.model.DeliveryServiceReactive
import org.lemo.kotlin.reactive.model.DeliveryServiceRepository
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

class StreamRouterConfigTest {
    @Test
    fun `Route returns delivery items`() {
        //given
        val repo : DeliveryServiceRepository = mock()
        whenever(repo.findByName("Something"))
            .thenReturn(listOf(DeliveryItem(itemName = "Something", quantity = 1)))
        //when
        val testClient =
            WebTestClient.bindToRouterFunction(
                StreamRouterConfig(DeliveryServiceReactive(repo))
                    .getOnly())
                .build()
        //then
        testClient.get().uri("/stream/delivery/Something")
            .exchange()
            .expectBody()
            .jsonPath("$").isArray
            .jsonPath("$").isNotEmpty
            .jsonPath("$[0].itemName").isEqualTo("Something")
            .jsonPath("$[1]").doesNotExist()
    }
}