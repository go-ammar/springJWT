package com.authorization.jwttoken.service

import com.authorization.jwttoken.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.authorization.jwttoken.model.User

@Service
@Qualifier("customUserDetailService")
class CustomUserDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByEmail(username)
        ?.mapToUserDetails() ?: throw UsernameNotFoundException("User not found!")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}
