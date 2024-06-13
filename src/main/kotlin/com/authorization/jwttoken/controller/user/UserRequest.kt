package com.authorization.jwttoken.controller.user

data class UserRequest(
    val email: String,
    val password: String
)
