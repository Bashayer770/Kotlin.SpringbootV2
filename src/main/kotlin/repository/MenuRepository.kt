package com.coded.spring.ordering.repository

import com.coded.spring.ordering.model.MenuItem
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<MenuItem, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<MenuItem>
}
