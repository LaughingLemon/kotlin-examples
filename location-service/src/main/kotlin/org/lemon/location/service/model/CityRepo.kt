package org.lemon.location.service.model

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "city", path = "city")
interface CityRepo: CrudRepository<City, Long> {
    fun findByName(@Param("name") name: String): List<City>
}