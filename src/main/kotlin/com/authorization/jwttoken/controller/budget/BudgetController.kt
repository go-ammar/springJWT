package com.authorization.jwttoken.controller.budget

import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.service.BudgetService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/budget")
class BudgetController(private val service: BudgetService) {

    @GetMapping()
    fun getAllBudget(): ResponseEntity<List<Budget>> {
        return ResponseEntity(service.getAllBudget(), HttpStatus.OK)
    }

    @GetMapping("user")
    fun getAllUserBudget(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<Budget>> {
        return ResponseEntity(service.getUserBudget(authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

    @PostMapping("create")
    fun createBudget(
        @Valid @RequestBody request: BudgetRequest,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Budget> {
        println("Authorization Header: ${authHeader.substringAfter("Bearer ")}")
        return ResponseEntity(service.createBudget(request, authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

    @GetMapping("/categories")
    fun getBudgetCategories(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<List<String>> {
        return ResponseEntity(service.getBudgetCategories(authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

    @PutMapping("/update")
    fun updateBudget(
        @Valid @RequestBody request: UpdateBudgetRequest,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Budget> {
        return ResponseEntity(service.updateBudget(request, authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }


    @DeleteMapping("/delete/{id}")
    fun deleteBudget(
        @PathVariable id : Long
    ): ResponseEntity<Void> {
        service.deleteBudget(id)
        return ResponseEntity.noContent().build()
    }



    @GetMapping("/monthly-spend")
    fun getMonthlySpendByUser(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<MonthlyCategorySpend>> {
        return ResponseEntity(service.getMonthlySpendByUser(authHeader.substringAfter("Bearer ")), HttpStatus.OK)
    }

}

