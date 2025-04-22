package com.coded.spring.ordering.repository

import com.coded.spring.ordering.model.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository : JpaRepository<ProfileEntity, Long> {
    fun findByUserUsername(username: String): ProfileEntity?
} 