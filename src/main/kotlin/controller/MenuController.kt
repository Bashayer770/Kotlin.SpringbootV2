package com.coded.spring.ordering.controller

import com.coded.spring.ordering.model.MenuItem
import com.coded.spring.ordering.repository.MenuRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/menu")
class MenuController(private val menuRepository: MenuRepository) {

    @GetMapping
    @Cacheable("menu")
    fun listMenu(@RequestParam(required = false) search: String?): List<MenuItem> {
        return if (search.isNullOrBlank()) {
            menuRepository.findAll()
        } else {
            menuRepository.findByNameContainingIgnoreCase(search)
        }
    }
}
