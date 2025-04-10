package com.coded.spring.ordering.model

data class Order(
    val user: String,
    val resturant: String,
    val items: List<String>
)
