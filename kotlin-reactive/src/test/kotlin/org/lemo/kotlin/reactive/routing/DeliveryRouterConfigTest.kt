package org.lemo.kotlin.reactive.routing

import org.junit.jupiter.api.Test
import org.lemo.kotlin.reactive.model.DeliveryItem
import org.lemo.kotlin.reactive.model.DeliveryServiceRepository
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.reactive.server.WebTestClient

internal class DeliveryRouterConfigTest {
    @Test
    fun `Route returns delivery items`() {
        //given
        val repo : DeliveryServiceRepository = mock()
        whenever(repo.findAll()).thenReturn(listOf(DeliveryItem(itemName = "Something", quantity = 1)))
        //when
        val testClient =
            WebTestClient.bindToRouterFunction(DeliveryRouterConfig(repo).allDeliveries()).build()
        //then
        testClient.get().uri("/reactive/deliveries")
            .exchange()
            .expectBody()
            .jsonPath("$").isArray
            .jsonPath("$").isNotEmpty
            .jsonPath("$[0].itemName").isEqualTo("Something")
            .jsonPath("$[1].itemName").doesNotExist()
    }
}