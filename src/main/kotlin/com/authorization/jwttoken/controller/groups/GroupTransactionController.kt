package com.authorization.jwttoken.controller.groups

import com.authorization.jwttoken.model.Group
import com.authorization.jwttoken.model.GroupTransaction
import com.authorization.jwttoken.repository.UserRepository
import com.authorization.jwttoken.service.GroupService
import com.authorization.jwttoken.service.GroupTransactionService
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class GroupTransactionController (
    private val groupTransactionService: GroupTransactionService,
    private val groupService: GroupService
) {

    @PostMapping("/transactions")
    fun addTransactionToGroup(
        @RequestBody addTransactionDTO: AddTransactionDTO
    ): ResponseEntity<GroupTransaction> {
        val transaction = groupTransactionService.addTransactionToGroup(
            groupId = addTransactionDTO.groupId,
            transactionDate = addTransactionDTO.transactionDate,
            description = addTransactionDTO.description,
            amountPaid = addTransactionDTO.amountPaid,
            paidByUserId = addTransactionDTO.paidByUserId,
            userShares = addTransactionDTO.userShares
        )
        return ResponseEntity.ok(transaction)
    }

    @GetMapping("/transactions")
    fun getTransactionsByUserId(
        @RequestParam userId: Long
    ): ResponseEntity<List<GroupTransaction>> {
        val transactions = groupTransactionService.getTransactionsByUserId(userId)
        return ResponseEntity.ok(transactions)
    }


//    @PostMapping("/create")
//    fun createGroup(
//        @RequestParam name: String,
//        @RequestParam createdByUserId: Long,
//        @RequestBody userIds: List<Long>
//    ): ResponseEntity<Group> {
//        val group = groupService.createGroup(
//            name = name,
//            createdByUserId = createdByUserId,
//            userIds = userIds
//        )
//        return ResponseEntity.ok(group)
//    }


    @PostMapping("/create")
    fun createGroup(@RequestBody request: CreateGroupRequest): ResponseEntity<Group> {
        val group = groupService.createGroup(
            name = request.name,
            createdByUserId = request.createdByUserId,
            userIds = request.userIds
        )
        return ResponseEntity.ok(group)
    }
}