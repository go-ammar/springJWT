package com.authorization.jwttoken.controller.user

import java.util.*

data class UpdateUserRequest(
    val email: String,
    var name: String,
    val dob: Long
)