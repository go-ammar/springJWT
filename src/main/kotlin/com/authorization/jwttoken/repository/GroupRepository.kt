package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.Group
import com.authorization.jwttoken.model.GroupTransaction
import com.authorization.jwttoken.model.GroupTransactionUserShare
import com.authorization.jwttoken.model.GroupUser
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long>

interface GroupTransactionRepository : JpaRepository<GroupTransaction, Long>

interface GroupUserRepository : JpaRepository<GroupUser, Long> {
    fun findByUserId(userId: Long): List<GroupUser>
}

interface GroupTransactionUserShareRepository : JpaRepository<GroupTransactionUserShare, Long> {
    fun findByUserId(userId: Long): List<GroupTransactionUserShare>

    fun findAllByUsers_Id(userId: Long): List<Group>
}