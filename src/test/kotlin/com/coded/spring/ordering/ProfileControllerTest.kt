package com.coded.spring.ordering

import com.coded.spring.ordering.controller.ProfileController
import com.coded.spring.ordering.dto.ProfileRequest
import com.coded.spring.ordering.dto.ProfileResponse
import com.coded.spring.ordering.service.ProfileService
import com.coded.spring.ordering.security.SecurityConfig
import com.coded.spring.ordering.security.JwtService
import com.coded.spring.ordering.security.JwtFilter
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(controllers = [ProfileController::class])
@Import(SecurityConfig::class)
class ProfileControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var profileService: ProfileService

    @MockBean
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser(username = "testuser")
    fun `create profile should return 200 OK when valid request`() {
        val request = ProfileRequest(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "12345678"
        )

        val response = ProfileResponse(
            id = 1L,
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "12345678",
            username = "testuser"
        )

        Mockito.`when`(profileService.createProfile("testuser", request))
            .thenReturn(response)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/profiles")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("12345678"))
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `create profile should return 400 when invalid phone number`() {
        val request = ProfileRequest(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1234567" // Invalid: less than 8 digits
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/profiles")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `create profile should return 400 when invalid first name`() {
        val request = ProfileRequest(
            firstName = "John1", // Invalid: contains number
            lastName = "Doe",
            phoneNumber = "12345678"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/profiles")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `get profile should return 200 OK when profile exists`() {
        val response = ProfileResponse(
            id = 1L,
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "12345678",
            username = "testuser"
        )

        Mockito.`when`(profileService.getProfile("testuser"))
            .thenReturn(response)

        mockMvc.perform(MockMvcRequestBuilders.get("/profiles"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("12345678"))
    }
} 