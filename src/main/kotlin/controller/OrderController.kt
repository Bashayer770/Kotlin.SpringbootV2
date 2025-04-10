package com.coded.spring.ordering.controller

import com.coded.spring.ordering.model.Order
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController {

    private val orders = mutableListOf<Order>()

    @PostMapping
    fun placeOrder(@RequestBody order: Order): String {
        orders.add(order)
        return "Order from ${order.user} to ${order.resturant} has been placed successfully!"
    }

    @GetMapping
    fun getAllOrders(): List<Order> {
        return orders
    }
}
