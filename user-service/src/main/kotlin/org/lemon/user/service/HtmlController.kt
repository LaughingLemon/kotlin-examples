package org.lemon.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView

@Controller
class HtmlController(private val repository: UserRepository) {

    @GetMapping("/")
    fun user(model: Model): String {
        model["title"] = "User"
        model["users"] = repository.findAll().map { it.render() }
        return "users"
    }

    @GetMapping("/user/create")
    fun createUser(model: Model): String {
        model["title"] = "Create New User"
        return "create"
    }

    @PostMapping("/user/create")
    fun createNewUser(@ModelAttribute newUser: User, model: Model): RedirectView {
        repository.save(newUser)
        return RedirectView("/")
    }

    @GetMapping("/user/{id}")
    fun display(@PathVariable id: Long, model: Model): String {
        val user = repository.findByIdOrNull(id)
            ?.render()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
        model["title"] = "User"
        model["firstname"] = user.firstName
        model["lastname"] = user.lastName
        model["email"] = user.email?:"None"
        return "display"
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