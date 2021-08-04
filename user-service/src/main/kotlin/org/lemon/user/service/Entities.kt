package org.lemon.user.service

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String? = null
)