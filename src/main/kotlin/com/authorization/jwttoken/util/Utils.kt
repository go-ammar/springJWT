package com.authorization.jwttoken.util

import com.authorization.jwttoken.model.User
import org.springframework.security.core.userdetails.UserDetails


object Utils {

    fun User.mapToUserDetails(): UserDetails =
        org.springframework.security.core.userdetails.User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.id.toString())
            .build()

}