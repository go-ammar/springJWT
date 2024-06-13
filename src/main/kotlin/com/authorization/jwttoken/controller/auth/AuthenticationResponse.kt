package com.authorization.jwttoken.controller.auth

data class AuthenticationResponse (
    val accessToken : String,
    val refreshToken: String
)
