package com.authorization.jwttoken.controller.user

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val dob: Long,
    var updatedToken: String = ""
)