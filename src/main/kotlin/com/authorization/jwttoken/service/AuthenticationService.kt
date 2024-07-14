package com.authorization.jwttoken.service

import com.authorization.jwttoken.config.JwtProperties
import com.authorization.jwttoken.controller.auth.AuthenticationRequest
import com.authorization.jwttoken.controller.auth.AuthenticationResponse
import com.authorization.jwttoken.exceptions.TokenNotFoundException
import com.authorization.jwttoken.repository.RefreshTokenRepository
import com.authorization.jwttoken.util.Utils.mapToUserDetails
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    @Qualifier("customUserDetailService") private val userDetailService: CustomUserDetailService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userService: UserService
) {

    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {

        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val user = userDetailService.loadUserByEmail(authenticationRequest.email)
        val userSpring = user.mapToUserDetails()

        val map: MutableMap<String, ApplicationUser> = mutableMapOf()
        map["user"] = user

        val accessToken = createAccessToken(userSpring, map)
        val refreshToken = createRefreshToken(userSpring)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id.toString()
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedEmail = tokenService.extractEmail(refreshToken)

        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailService.loadUserByEmail(email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

            val currentUserDetailsSpring = currentUserDetails.mapToUserDetails()


            val map: MutableMap<String, ApplicationUser> = mutableMapOf()
            map["user"] = currentUserDetails

            currentUserDetails.mapToUserDetails()
            if (!tokenService.isExpired(refreshToken) && refreshTokenUserDetails?.email == currentUserDetailsSpring.username)
                createAccessToken(currentUserDetailsSpring, map)
            else
                throw TokenNotFoundException()
        }
    }

    private fun createAccessToken(user: UserDetails, userObj: MutableMap<String, ApplicationUser>) =
        tokenService.generate(
            userDetails = user,
            expirationDate = getAccessTokenExpiration(),
            additionalClaims = userObj
        )

    private fun createRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

}
