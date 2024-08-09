package com.authorization.jwttoken.repository

import com.authorization.jwttoken.controller.budget.MonthlyCategorySpend
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

    @Query(value = "SELECT * FROM budget WHERE user_id = :userId AND category = :category", nativeQuery = true)
    fun findByCategoryAndUser(@Param("category") category: String, @Param("userId") userId: String): List<Budget?>

    @Query(value = "SELECT category FROM budget WHERE user_id = :userId", nativeQuery = true)
    fun findCategoryByUser(@Param("userId") userId: String): List<String>?

    @Modifying
    @Query("UPDATE Budget b SET b.category = :category, b.amount = :amount WHERE b.id = :id")
    fun updateBudget(
        @Param("id") id: String,
        @Param("amount") amount: String,
        @Param("category") category: String,
//        @Param("months") months: String
    )

    @Modifying
    @Query("delete from Budget b where b.id = :id")
    fun deleteBudget(@Param("id") id: String)


    @Query("""
        SELECT 
            t.category AS category,
            MONTH(FROM_UNIXTIME(t.transaction_date / 1000)) AS month,
            YEAR(FROM_UNIXTIME(t.transaction_date / 1000)) AS year,
            SUM(t.amount) AS totalAmount,
            b.amount AS budgetAmount
        FROM 
            transactions t
        JOIN 
            budget b ON t.category = b.category AND t.user_id = b.user_id
        WHERE 
            t.user_id = :userId
        GROUP BY 
            t.category, 
            YEAR(FROM_UNIXTIME(t.transaction_date / 1000)), 
            MONTH(FROM_UNIXTIME(t.transaction_date / 1000))
        ORDER BY 
            t.category, 
            year, 
            month
    """, nativeQuery = true)
    fun findMonthlyCategorySpendByUserId(@Param("userId") userId: String): List<Array<Any>>


}