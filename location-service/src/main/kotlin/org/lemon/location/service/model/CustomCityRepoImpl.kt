package org.lemon.location.service.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class CustomCityRepoImpl : CustomCityRepo {

    @Autowired
    @Lazy
    private lateinit var cityRepo: CityRepo

    override fun findNearestWithLimit(lat: Double, long: Double, limit: Int): List<City> {

        fun distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
            val phi1 = lat1 * Math.PI / 180
            val phi2 = lat2 * Math.PI / 180
            val delta = (long2 - long1) * Math.PI / 180
            val r = 6371.00
            return acos(sin(phi1) * sin(phi2) +
                        cos(phi1) * cos(phi2) * cos(delta)) * r
        }

        return cityRepo
            .findAll()
            .sortedBy { distance(it.lat ?: 0.0, it.long ?: 0.0, lat, long) }
            .subList(0, limit)
    }

    override fun findNearest(lat: Double, long: Double): City? {
        return findNearestWithLimit(lat, long, 1).firstOrNull()
    }


}