package org.lemo.kotlin.reactive.routing

import org.lemo.kotlin.reactive.model.DeliveryItem
import org.lemo.kotlin.reactive.model.DeliveryServiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@Configuration
open class DeliveryRouterConfig(@Autowired val repository: DeliveryServiceRepository) {

    @Bean
    open fun allDeliveries() = router {
        GET("/reactive/deliveries") {
            _ -> request()
        }
    }

    private fun request() =
        ServerResponse.ok()
                      .body(Flux.fromIterable(repository.findAll())
                                .take(12),
                            DeliveryItem::class.java)
}