package com.coded.spring.ordering.service

import com.coded.spring.ordering.dto.ProfileRequest
import com.coded.spring.ordering.dto.ProfileResponse
import com.coded.spring.ordering.model.ProfileEntity
import com.coded.spring.ordering.model.UserEntity
import com.coded.spring.ordering.repository.ProfileRepository
import com.coded.spring.ordering.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createProfile(username: String, request: ProfileRequest): ProfileResponse {
        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("User not found")

        if (profileRepository.findByUserUsername(username) != null) {
            throw RuntimeException("Profile already exists for this user")
        }

        val profile = ProfileEntity(
            user = user,
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber
        )

        val savedProfile = profileRepository.save(profile)
        return ProfileResponse(
            id = savedProfile.id,
            firstName = savedProfile.firstName,
            lastName = savedProfile.lastName,
            phoneNumber = savedProfile.phoneNumber,
            username = savedProfile.user.username
        )
    }

    fun getProfile(username: String): ProfileResponse {
        val profile = profileRepository.findByUserUsername(username)
            ?: throw RuntimeException("Profile not found")

        return ProfileResponse(
            id = profile.id,
            firstName = profile.firstName,
            lastName = profile.lastName,
            phoneNumber = profile.phoneNumber,
            username = profile.user.username
        )
    }
} 