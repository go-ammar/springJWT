package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.model.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<TransactionHistory, Long> {


    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId", nativeQuery = true)
    fun getUserTransactions(@Param("userId") userId: String): List<TransactionHistory>



}