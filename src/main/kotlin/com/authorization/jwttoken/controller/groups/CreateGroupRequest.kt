package com.authorization.jwttoken.controller.groups

data class CreateGroupRequest(
    val name: String,
    val createdByUserId: Long,
    val userIds: List<Long>
)