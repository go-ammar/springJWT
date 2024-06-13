package com.authorization.jwttoken.controller.budget

import com.authorization.jwttoken.model.Budget
import com.authorization.jwttoken.service.BudgetService
import jakarta.validation.Valid
import org.apache.coyote.Response
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

    @PostMapping("create")
    fun createBudget(@Valid @RequestBody request: BudgetRequest): ResponseEntity<Budget> {
        return ResponseEntity(service.createBudget(request), HttpStatus.OK)
    }

}

