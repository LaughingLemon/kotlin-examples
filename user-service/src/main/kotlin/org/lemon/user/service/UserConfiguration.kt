package org.lemon.user.service

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UserConfiguration {

    @Bean
    open fun databaseInitializer(userRepository: UserRepository) = ApplicationRunner {
        userRepository.save(User(firstName = "Frank", lastName = "Jones", email = "fjones@email.com"))
    }
}