package com.authorization.jwttoken.service

import com.authorization.jwttoken.controller.budget.BudgetRequest
import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.repository.BudgetRepository
import org.springframework.stereotype.Service

@Service
class BudgetService(private val repository: BudgetRepository) {

    fun getAllBudget(): List<Budget> {
        return repository.findAll()
    }

    fun createBudget(createRequest: BudgetRequest): Budget {
        val budget = Budget(
            category = createRequest.category,
            months = createRequest.months,
            amount = createRequest.amount,
            userId = createRequest.userId
        )

        val savedBudget = repository.save(budget)
        return savedBudget
    }

}