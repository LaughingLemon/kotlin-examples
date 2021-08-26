package org.lemon.location.service.model

import org.springframework.data.repository.query.Param

interface CustomCityRepo {
    fun findNearestWithLimit(@Param("lat") lat: Double,
                    @Param("long") long: Double,
                    @Param("limit") limit: Int): List<City>
    fun findNearest(@Param("lat") lat: Double,
                    @Param("long") long: Double): City?
}