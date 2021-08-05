package org.lemon.user.service.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ITUserPage(@Autowired val restTemplate: TestRestTemplate) {

	@Test
	fun `Assert user page title, content and status code`() {
		val entity = restTemplate.getForEntity<String>("/")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body)
			.contains("<h1>User</h1>", "<title>User</title>")
	}

	@Test
	fun `Assert user page loads`() {
		val entity = restTemplate.getForEntity<String>("/user/1")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body)
			.contains("<p>Name: Frank Jones</p>", "<p>email: fjones@email.com</p>")
	}

}
