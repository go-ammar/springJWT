package com.authorization.jwttoken.controller.budget

data class UpdateBudgetRequest(
    val id : Long,
    val category: String,
    val months: String,
    val amount: Int
)
