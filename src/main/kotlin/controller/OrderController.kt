package com.coded.spring.ordering.controller

import com.coded.spring.ordering.model.OrderEntity
import com.coded.spring.ordering.repository.OrderRepository
import org.springframework.web.bind.annotation.*

data class OrderDTO(
    val user: String,
    val restuarant: String,
    val items: List<String>
)

@RestController
@RequestMapping("/orders")
class OrderController(private val orderRepository: OrderRepository) {

    @PostMapping
    fun placeOrder(@RequestBody dto: OrderDTO): String {
        val concatenatedItems = dto.items.joinToString(", ")
        val order = OrderEntity(user = dto.user, restuarant = dto.restuarant, items = concatenatedItems)
        orderRepository.save(order)
        return "✅ Order from ${dto.user} has been saved!"
    }

    @GetMapping
    fun getAllOrders(): List<OrderEntity> = orderRepository.findAll()
}
