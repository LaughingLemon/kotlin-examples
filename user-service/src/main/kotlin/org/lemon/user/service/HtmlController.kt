package org.lemon.user.service

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController(private val repository: UserRepository) {

    @GetMapping("/")
    fun user(model: Model): String {
        model["title"] = "User"
        model["users"] = repository.findAll().map { it.render() }
        return "user"
    }

    fun User.render() = RenderedUser(
        id,
        firstName,
        lastName,
        email
    )

    data class RenderedUser(
        val id: Long?,
        val firstName: String,
        val lastName: String,
        val email: String?
    )
}