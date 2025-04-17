package com.coded.spring.ordering.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController {

    @GetMapping("/")
    fun welcome(): String {
        return """
            🍽️ Welcome to QuickBite!

            Your new favorite food ordering platform.
            Built with Kotlin + Spring Boot.
            Ready to serve you like Talabat, Deliveroo, and Carriage
        """.trimIndent()
    }
}
