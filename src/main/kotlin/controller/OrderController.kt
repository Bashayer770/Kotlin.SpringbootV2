package com.coded.spring.ordering.controller
import com.coded.spring.ordering.model.UserEntity
import com.coded.spring.ordering.model.OrderEntity
import com.coded.spring.ordering.repository.OrderRepository
import com.coded.spring.ordering.repository.UserRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus



data class OrderDTO(
    val user: String,
    val restuarant: String,
    val items: List<String>
)
data class RegisterDTO(val username: String, val password: String)
@RestController
@RequestMapping("/orders")
class OrderController(private val orderRepository: OrderRepository, private val userRepository: UserRepository) {

    @PostMapping
    fun placeOrder(@RequestBody dto: OrderDTO): String {
        val concatenatedItems = dto.items.joinToString(", ")
        val order = OrderEntity(user = dto.user, restuarant = dto.restuarant, items = concatenatedItems)
        orderRepository.save(order)
        return "✅ Order from ${dto.user} has been saved!"
    }

    @GetMapping
    fun getAllOrders(): List<OrderEntity> = orderRepository.findAll()

    private fun isValidPassword(password: String): Boolean {
        val hasCapital = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= 6 && hasCapital && hasDigit
    }
    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterDTO): String {
        if (userRepository.findByUsername(dto.username) != null)
            throw ResponseStatusException(HttpStatus.CONFLICT, "Username already taken")

        if (!isValidPassword(dto.password))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Weak password: must be ≥6 characters, 1 capital letter, and 1 number")

        val user = UserEntity(username = dto.username, password = dto.password)
        userRepository.save(user)
        return " Registered successfully!"
    }




}
