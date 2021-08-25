package org.lemon.location.service.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy

class CustomCityRepoImpl : CustomCityRepo {

    @Autowired
    @Lazy
    private lateinit var cityRepo: CityRepo

    override fun findNearest(lat: Double, long: Double, limit: Int): List<City> {

        fun distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
            val phi1 = lat1 * Math.PI / 180
            val phi2 = lat2 * Math.PI / 180
            val delta = (long2 - long1) * Math.PI / 180
            val r = 6371.00
            val dist =
                Math.acos(Math.sin(phi1) * Math.sin(phi2) +
                          Math.cos(phi1) * Math.cos(phi2) * Math.cos(delta)) * r
            return dist
        }

        return cityRepo
            .findAll()
            .sortedBy { distance(it.lat ?: 0.0, it.long ?: 0.0, lat, long) }
            .subList(0, limit)
    }


}