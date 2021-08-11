package org.lemo.kotlin.reactive.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class DeliveryServiceRepositoryTests
@Autowired
constructor(val entityManager: TestEntityManager,
            val deliveryServiceRepository: DeliveryServiceRepository)
{
    @Test
    fun `test find by item name`() {
        val testItem = DeliveryItem(itemName = "Pants", quantity = 12)
        entityManager.persist(testItem)
        entityManager.flush()

        val item = deliveryServiceRepository.findByName("Pants")
        assertThat(item).isNotEmpty.contains(testItem)
        val nullItem = deliveryServiceRepository.findByName("Aardvark")
        assertThat(nullItem).isEmpty()
    }
}