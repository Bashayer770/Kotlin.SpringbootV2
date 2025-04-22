package com.coded.spring.ordering.dto

import jakarta.validation.constraints.Pattern

data class ProfileRequest(
    @field:Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name can only contain letters and spaces")
    val firstName: String,

    @field:Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Last name can only contain letters and spaces")
    val lastName: String,

    @field:Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits")
    val phoneNumber: String
)

data class ProfileResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val username: String
) 