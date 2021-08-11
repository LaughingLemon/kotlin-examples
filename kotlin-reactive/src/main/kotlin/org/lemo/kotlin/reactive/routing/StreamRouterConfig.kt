package org.lemo.kotlin.reactive.routing

import org.lemo.kotlin.reactive.model.DeliveryItem
import org.lemo.kotlin.reactive.model.DeliveryServiceReactive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
open class StreamRouterConfig(@Autowired val repo : DeliveryServiceReactive) {

    @Bean
    fun getOnly() = router {
        GET("/stream/delivery/{itemName}") {
            request -> getDelivery(request)
        }
    }

    private fun getDelivery(request: ServerRequest): Mono<ServerResponse> {
        val itemName = request.pathVariable("itemName")
        return ServerResponse.ok().body(repo.getItemByName(itemName), DeliveryItem::class.java)
    }

}