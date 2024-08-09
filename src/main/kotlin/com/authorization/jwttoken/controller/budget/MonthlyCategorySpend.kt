package com.authorization.jwttoken.controller.budget

data class MonthlyCategorySpend(
    val category: String,
    val month: Int,
    val year: Int,
    val totalAmount: Int,
    val budgetAmount : Int
)