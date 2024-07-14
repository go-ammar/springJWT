package com.authorization.jwttoken.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val authenticationProvider: AuthenticationProvider) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain = http.csrf {
        it.disable()
    }
        .authorizeHttpRequests {
            it
                .requestMatchers("/api/auth", "api/auth/refresh", "/error", "/api/budget/**","/api/user/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/user", "/api/budget/create")
                .permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/budget/update")
                .permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/budget/delete**")
                .permitAll()
                .requestMatchers("/api/user**")
                .fullyAuthenticated()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()
}