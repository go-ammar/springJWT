package com.authorization.jwttoken.controller.user

import com.authorization.jwttoken.model.Role
import com.authorization.jwttoken.model.User
import com.authorization.jwttoken.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping()
    fun createUser(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(
            user = userRequest.toModel()
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make user.")


    @GetMapping
    fun getAllUser(): List<UserResponse> =
        userService.findAll().map {
            it.toResponse()
        }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): UserResponse {
        return userService.findById(id)?.toResponse() ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Cannot Find User."
        )
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Boolean> {
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
            id = UUID.randomUUID(),
            this.email,
            this.password,
            Role.USER
        )

    private fun User.toResponse(): UserResponse =
        UserResponse(
            id = this.id,
            email = this.email
        )


}

