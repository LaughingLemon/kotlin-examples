package org.lemo.kotlin.reactive.routing

import org.lemo.kotlin.reactive.model.DeliveryItem
import org.lemo.kotlin.reactive.model.DeliveryServiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Configuration
open class DeliveryRouterConfig(@Autowired val repository: DeliveryServiceRepository) {

    @Bean
    open fun allDeliveries() : RouterFunction<ServerResponse> {
        return route(GET("/reactive/deliveries"), ::recent)
    }

    private fun recent(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(Flux.fromIterable(repository.findAll()).take(12),
                  DeliveryItem::class.java)
    }
}