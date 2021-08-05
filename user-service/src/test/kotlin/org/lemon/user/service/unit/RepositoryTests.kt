package org.lemon.user.service.unit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.lemon.user.service.User
import org.lemon.user.service.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository
) {

    @Test
    fun `when fiindByEmail return user`() {
        val dave = User(firstName = "David",
                        lastName = "Jones",
                        email = "djones@email.com")
        entityManager.persist(dave)
        entityManager.flush()
        val user = userRepository.findByEmail("djones@email.com")
        assertThat(user).isEqualTo(dave)
        val nullUser = userRepository.findByEmail("aaron@aardvark.com")
        assertThat(nullUser).isNull()
    }

}