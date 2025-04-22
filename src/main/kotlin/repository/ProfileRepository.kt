package com.coded.spring.ordering.repository

import com.coded.spring.ordering.model.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<ProfileEntity, Long> {
    fun findByUserUsername(username: String): ProfileEntity?
} 