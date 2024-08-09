package com.authorization.jwttoken.controller.budget

data class BudgetRequest(
    val category: String,
//    val months: String,
    val amount: Int,
//    val userId: Long
)
