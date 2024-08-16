package com.authorization.jwttoken.service

import com.authorization.jwttoken.model.GroupTransaction
import com.authorization.jwttoken.model.GroupTransactionUserShare
import com.authorization.jwttoken.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GroupTransactionService (
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val groupTransactionRepository: GroupTransactionRepository,
    private val groupTransactionUserShareRepository: GroupTransactionUserShareRepository,
    private val groupUserRepository: GroupUserRepository
) {

    @Transactional
    fun addTransactionToGroup(
        groupId: Long,
        transactionDate: Long,
        description: String,
        amountPaid: Int,
        paidByUserId: Long,
        userShares: Map<Long, Int> // Key: userId, Value: share amount
    ): GroupTransaction {
        val group = groupRepository.findById(groupId)
            .orElseThrow { RuntimeException("Group not found") }

        val paidByUser = userRepository.findById(paidByUserId)
            .orElseThrow { RuntimeException("User not found") }

        val groupTransaction = GroupTransaction(
            group = group,
            transactionDate = transactionDate,
            description = description,
            amountPaid = amountPaid,
            paidByUser = paidByUser
        )

        val savedTransaction = groupTransactionRepository.save(groupTransaction)

        userShares.forEach { (userId, shareAmount) ->
            val user = userRepository.findById(userId)
                .orElseThrow { RuntimeException("User not found") }

            val share = GroupTransactionUserShare(
                groupTransaction = savedTransaction,
                user = user,
                shareAmount = shareAmount
            )
            groupTransactionUserShareRepository.save(share)
        }

        return savedTransaction
    }

    fun getTransactionsByUserId(userId: Long): List<GroupTransaction> {
        val userShares = groupTransactionUserShareRepository.findByUserId(userId)
        return userShares.map { it.groupTransaction }
    }
}