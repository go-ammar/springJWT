package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.Budget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BudgetRepository : JpaRepository<Budget, Long> {

    @Query(value = "SELECT * FROM budget WHERE user_id = :userId", nativeQuery = true)
    fun getUserBudgets(@Param("userId") userId: String): List<Budget>

    @Modifying
    @Query("UPDATE Budget b SET b.category = :category, b.months = :months, b.amount = :amount WHERE b.id = :id")
    fun updateBudget(
        @Param("id") id: String,
        @Param("amount") amount: String,
        @Param("category") category: String,
        @Param("months") months: String
    )

    @Modifying
    @Query("UPDATE Budget b SET b.category = :category, b.months = :months, b.amount = :amount WHERE b.id = :id")
    fun deleteBudget(
        @Param("id") id: String)

}