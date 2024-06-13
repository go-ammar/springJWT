package com.authorization.jwttoken.controller.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)
