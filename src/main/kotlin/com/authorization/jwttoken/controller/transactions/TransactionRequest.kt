package com.authorization.jwttoken.controller.transactions

data class TransactionRequest(
    val category: String,
    val transactionDate: Long,
    val amount: Int
)
