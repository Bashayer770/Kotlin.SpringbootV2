package com.coded.spring.ordering.repository



import com.coded.spring.ordering.model.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long>