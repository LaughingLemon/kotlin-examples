package org.lemo.kotlin.reactive.model

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface DeliveryServiceRepository: CrudRepository<DeliveryItem, Long> {
    @Query("select t from DeliveryItem t where t.itemName = :itemName")
    fun findByName(@Param("itemName") itemName: String): List<DeliveryItem>
}