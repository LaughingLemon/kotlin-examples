package org.lemo.kotlin.reactive.model

import javax.persistence.*

@Entity
@Table(name = "DELIVERY_ITEM")
class DeliveryItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(name = "ITEM_NAME")
    val itemName: String,
    val quantity: Long
)
