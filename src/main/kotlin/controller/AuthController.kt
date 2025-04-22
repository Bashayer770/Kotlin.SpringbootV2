package com.coded.spring.ordering.controller

import com.coded.spring.ordering.model.UserEntity
import com.coded.spring.ordering.repository.UserRepository
import com.coded.spring.ordering.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class AuthRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val token: String
)

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        
        val userDetails = User.builder()
            .username(request.username)
            .password("{noop}${request.password}")
            .roles("USER")
            .build()
            
        val token = jwtService.generateToken(userDetails.username)
        return ResponseEntity.ok(AuthResponse(token))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        if (userRepository.findByUsername(request.username) != null) {
            throw RuntimeException("Username already exists")
        }

        val user = UserEntity(
            username = request.username,
            password = passwordEncoder.encode(request.password)
        )
        userRepository.save(user)

        val userDetails = User.builder()
            .username(request.username)
            .password("{noop}${request.password}")
            .roles("USER")
            .build()

        val token = jwtService.generateToken(userDetails.username)
        return ResponseEntity.ok(AuthResponse(token))
    }
}

