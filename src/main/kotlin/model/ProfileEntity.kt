package com.coded.spring.ordering.model

import jakarta.persistence.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Entity
@Table(name = "profiles")
data class ProfileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: UserEntity,

    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name can only contain letters and spaces")
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Last name can only contain letters and spaces")
    val lastName: String,

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits")
    val phoneNumber: String
) 