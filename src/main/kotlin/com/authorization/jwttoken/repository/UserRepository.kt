package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.Role
import com.authorization.jwttoken.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(
    private val encoder: PasswordEncoder
) {

    private val users = mutableListOf(
        User(
            id = UUID.randomUUID(),
            email = "user.gmail.com",
            password = encoder.encode("pass1"),
            role = Role.USER
        ),
        User(
            id = UUID.randomUUID(),
            email = "user1.gmail.com",
            password = encoder.encode("pass1"),
            role = Role.USER
        ),
        User(
            id = UUID.randomUUID(),
            email = "user2.gmail.com",
            password = encoder.encode("pass1"),
            role = Role.USER
        ),
        User(
            id = UUID.randomUUID(),
            email = "admin.gmail.com",
            password = encoder.encode("pass"),
            role = Role.ADMIN
        )
    )

    fun saveUser(user: User): Boolean {
        val updatedUser = user.copy(password = encoder.encode(user.password))
        return users.add(updatedUser)
    }

    fun findByEmail(email: String): User? =
        users.firstOrNull {
            it.email == email
        }

    fun findAll(): List<User> = users

    fun findById(id: UUID): User? =
        users.firstOrNull {
            it.id == id
        }

    fun deleteUserById(id: UUID): Boolean {
        val user = findById(id)
        return user.let {
            users.remove(it)
        }
    }

}