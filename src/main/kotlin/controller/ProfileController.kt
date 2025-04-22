package com.coded.spring.ordering.controller

import com.coded.spring.ordering.dto.ProfileRequest
import com.coded.spring.ordering.dto.ProfileResponse
import com.coded.spring.ordering.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles")
class ProfileController(
    private val profileService: ProfileService
) {

    @PostMapping
    fun createProfile(
        authentication: Authentication,
        @RequestBody request: ProfileRequest
    ): ResponseEntity<ProfileResponse> {
        val username = authentication.name
        val response = profileService.createProfile(username, request)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getProfile(authentication: Authentication): ResponseEntity<ProfileResponse> {
        val username = authentication.name
        val response = profileService.getProfile(username)
        return ResponseEntity.ok(response)
    }
} 