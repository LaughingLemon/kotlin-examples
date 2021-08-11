package org.lemo.kotlin.reactive.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
open class DeliveryServiceReactive(@Autowired val repo: DeliveryServiceRepository) {
    fun getItemByName(itemName: String) = Flux.fromIterable(repo.findByName(itemName))
}