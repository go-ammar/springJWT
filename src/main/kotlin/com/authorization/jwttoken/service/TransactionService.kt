package com.authorization.jwttoken.service

import com.authorization.jwttoken.controller.transactions.TransactionRequest
import com.authorization.jwttoken.model.TransactionHistory
import com.authorization.jwttoken.repository.TransactionRepository
import com.authorization.jwttoken.util.JwtUtil
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val repository: TransactionRepository,
    private val jwtUtil: JwtUtil
) {

    fun getUserTransactions(token: String): List<TransactionHistory> {
        val user = jwtUtil.extractUser(token)
        return repository.getUserTransactions(user?.id.toString())
    }

    fun createTransaction(createRequest: TransactionRequest, token: String): TransactionHistory {

        println("user is " + jwtUtil.extractUser(token))

        val transaction = TransactionHistory(
            category = createRequest.category,
            transactionDate = createRequest.transactionDate,
            amount = createRequest.amount,
            userId = jwtUtil.extractUser(token)!!.id.toString()
        )

        println("Budget is: $transaction")
        val savedBudget = repository.save(transaction)
        return savedBudget
    }

}