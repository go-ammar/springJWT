package com.authorization.jwttoken.controller.transactions

import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.model.TransactionHistory
import com.authorization.jwttoken.service.BudgetService
import com.authorization.jwttoken.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction")
class TransactionsController (private val service: TransactionService){

    @GetMapping()
    fun getAllUserTransactions(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<TransactionHistory>> {
        return ResponseEntity(service.getUserTransactions(authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

    @PostMapping()
    fun postUserTransactions(@Valid @RequestBody request: TransactionRequest, @RequestHeader("Authorization") authHeader: String): ResponseEntity<TransactionHistory> {
        return ResponseEntity(service.createTransaction(request,authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

}