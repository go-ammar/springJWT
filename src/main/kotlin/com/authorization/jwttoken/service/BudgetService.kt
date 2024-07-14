package com.authorization.jwttoken.service

import com.authorization.jwttoken.controller.budget.BudgetRequest
import com.authorization.jwttoken.controller.budget.UpdateBudgetRequest
import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.repository.BudgetRepository
import com.authorization.jwttoken.util.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BudgetService(
    private val repository: BudgetRepository,
    private val jwtUtil: JwtUtil
) {

    fun getUserBudget(token: String): List<Budget> {
        val user = jwtUtil.extractUser(token)
        return repository.getUserBudgets(user?.id.toString())
    }

    fun getAllBudget(): List<Budget> {
        return repository.findAll()
    }

    fun createBudget(createRequest: BudgetRequest, token: String): Budget {

        println("user is " + jwtUtil.extractUser(token))

        val budget = Budget(
            category = createRequest.category,
            months = createRequest.months,
            amount = createRequest.amount,
            userId = jwtUtil.extractUser(token)!!.id.toString()
        )

        println("Budget is: $budget")
        val savedBudget = repository.save(budget)
        return savedBudget
    }

    @Transactional
    fun updateBudget(updateRequest: UpdateBudgetRequest, token: String): Budget {
        println("Budget is: $updateRequest")
        repository.updateBudget(
            id = updateRequest.id.toString(),
            amount = updateRequest.amount.toString(),
            category = updateRequest.category,
            months = updateRequest.months
        )

        val userId = jwtUtil.extractUser(token)!!.id.toString()
        val savedBudget = Budget(
            id = updateRequest.id,
            amount = updateRequest.amount,
            category = updateRequest.category,
            months = updateRequest.months,
            userId = userId
        )
        return savedBudget
    }

    @Transactional
    fun deleteBudget(id: Long) {
        repository.deleteById(id)
    }

}