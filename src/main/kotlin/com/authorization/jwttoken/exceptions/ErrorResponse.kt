package com.authorization.jwttoken.exceptions

data class ErrorResponse(
    val error: Int,
    val message: String
)
