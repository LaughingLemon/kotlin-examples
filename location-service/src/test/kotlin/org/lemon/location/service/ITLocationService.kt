package org.lemon.location.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.lemon.location.service.model.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ITLocationService(@Autowired val restTemplate: TestRestTemplate) {

	@Test
	fun `Location near London`() {
		val entity = restTemplate.getForObject<City>("/city/search/findNearest?long=0.0&lat=52.0")
		assertThat(entity?.name).contains("London")
		assertThat(entity?.country).contains("United Kingdom")
	}

	@Test
	fun `Location on Equator`() {
		val entity = restTemplate.getForObject<City>("/city/search/findNearest?long=0.0&lat=0.0")
		assertThat(entity?.name).contains("Accra")
		assertThat(entity?.country).contains("Ghana")
	}

	@Test
	fun `Location in Antipodes`() {
		val entity = restTemplate.getForObject<City>("/city/search/findNearest?long=180.0&lat=-52.0")
		assertThat(entity?.name).contains("Wellington")
		assertThat(entity?.country).contains("New Zealand")
	}

	@Test
	fun `Location in South America`() {
		val entity = restTemplate.getForObject<City>("/city/search/findNearest?long=-60.0&lat=-20.0")
		assertThat(entity?.name).contains("Santa Cruz")
		assertThat(entity?.country).contains("Bolivia")
	}

	@Test
	fun `Location in South America With Limit`() {
		val objectMapper = ObjectMapper()
		val typeRef = object : TypeReference<List<City>>() {}
		val reader = objectMapper.readerFor(typeRef)
		val entity =
			restTemplate.getForEntity<String>("/city/search/findNearestWithLimit?long=-60.0&lat=-20.0&limit=3")
		val embedded = objectMapper.readTree(entity.body)
		val citiesArray = embedded.at("/_embedded/city") as ArrayNode
		val cities: List<City> = reader.readValue(citiesArray)
		assertThat(cities).isNotNull.hasSize(3).extracting("name").contains("Asuncion", "Sucre", "Santa Cruz")
	}
}
