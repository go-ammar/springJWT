package com.authorization.jwttoken.util

import com.authorization.jwttoken.model.User
import com.google.gson.Gson
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtUtil {

    @Value("\${jwt.key}")
    private lateinit var secret: String

    private fun getSigningKey(): SecretKey? {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun extractUserId(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        val key = getSigningKey()
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token).payload
    }

    fun extractUser(token: String): User? {
        val claims = extractAllClaims(token)
        return Gson().fromJson(claims["user"].toString(), User::class.java)
    }

}