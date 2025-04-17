package com.coded.spring.ordering.model

import jakarta.persistence.*

@Entity
@Table(name = "menu")
data class MenuItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val price: Double
)
