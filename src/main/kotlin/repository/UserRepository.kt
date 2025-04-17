package com.coded.spring.ordering.repository

import com.coded.spring.ordering.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
interface UserRepository: JpaRepository<UserEntity,Long>{
    fun findByUsername(username: String): UserEntity?
}