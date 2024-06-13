package com.authorization.jwttoken.service

import com.authorization.jwttoken.model.User
import com.authorization.jwttoken.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User): User? {
        val userFound = userRepository.findByEmail(user.email)
        if (userFound == null) {
            userRepository.saveUser(user)
            return user
        } else {
            return null
        }
    }

    fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun deleteById(id: UUID): Boolean {
        return userRepository.deleteUserById(id)
    }

}