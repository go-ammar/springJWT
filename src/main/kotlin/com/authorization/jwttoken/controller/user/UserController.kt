package com.authorization.jwttoken.controller.user

import com.authorization.jwttoken.model.User
import com.authorization.jwttoken.service.UserService
import com.authorization.jwttoken.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(
    private val userService: UserService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): UserResponse =
        userService.saveUser(
            user = userRequest.toModel()
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make user.")


    @GetMapping("/user")
    fun getUserDetails(@RequestHeader("Authorization") token: String): Map<String, String> {
        val jwt = token.substring(7) // Remove "Bearer " prefix
        val userId = jwtUtil.extractUserId(jwt)
        return mapOf("userId" to userId)
    }

    @GetMapping
    fun getAllUser(): List<UserResponse> =
        userService.findAll().map {
            it.toResponse()
        }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserResponse {
        return userService.findById(id).toResponse()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Boolean> {
        val success = userService.deleteById(id)
        return if (success) {
            ResponseEntity.noContent()
                .build()
        } else {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot Find User."
            )
        }
    }

    private fun UserRequest.toModel(): User =
        User(
            email = this.email,
            password = this.password,
        )

    private fun User.toResponse(): UserResponse =
        UserResponse(
            id = this.id,
            email = this.email
        )


}

