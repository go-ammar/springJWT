package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository {


    private val tokens = mutableMapOf<String, User>()

    fun findUserDetailsByToken(token: String): User? {
        return tokens[token]
    }

    fun save(token: String, userDetails: User) {
        tokens[token] = userDetails
    }

}