package com.authorization.jwttoken.util

import com.authorization.jwttoken.model.User
import com.google.gson.Gson
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
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
        val x = claims["user"] as String?
        return if (x != null) {
            val user = Gson().fromJson(x, User::class.java)
            println(user)
            user
        } else {
            println("User data is missing in claims")
            null
        }

//        return user
    }

    fun updateUser(token: String, ): User? {
        val claims = extractAllClaims(token)

        val x = claims["user"] as String?
        return if (x != null) {
            val user = Gson().fromJson(x, User::class.java)
            println(user)
            user
        } else {
            println("User data is missing in claims")
            null
        }

//        return user
    }

    fun updateClaims(oldToken: String, additionalClaims: Map<String, Any>): String {
        // Extract old claims
        val oldClaims = extractAllClaims(oldToken)
        val newClaims = oldClaims.toMutableMap()  // Convert to a mutable map

        // Add or update additional claims
        newClaims.putAll(additionalClaims)

        // Generate a new token with updated claims
        return Jwts.builder()
            .claims(newClaims)
            .subject(oldClaims.subject)
            .issuedAt(oldClaims.issuedAt)
            .expiration(oldClaims.expiration)
//            .add(additionalClaims)
//            .and()
            .signWith(getSigningKey())
            .compact()
    }

}