package org.lemon.user.service

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun user(model: Model): String {
        model["title"] = "User"
        return "user"
    }
}