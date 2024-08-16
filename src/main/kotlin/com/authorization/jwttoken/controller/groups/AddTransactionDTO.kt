package com.authorization.jwttoken.controller.groups

data class AddTransactionDTO(
    val transactionDate: Long,
    val description: String,
    val amountPaid: Int,
    val paidByUserId: Long,
    val groupId: Long,
    val userShares: Map<Long, Int>
)
