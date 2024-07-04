package com.authorization.jwttoken.service

import com.authorization.jwttoken.exceptions.UserExistsException
import com.authorization.jwttoken.exceptions.UserNotFoundException
import com.authorization.jwttoken.model.User
import com.authorization.jwttoken.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {


    @Transactional
    fun saveUser(user: User): User? {
        // Encode the password before saving
        val userFound = userRepository.findByEmail(user.email)
        if (userFound == null) {
            user.password = passwordEncoder.encode(user.password)
            return userRepository.save(user)
        } else {
            throw UserExistsException(user.email)
        }
    }


    fun findById(id: Long): User {
        val todo = userRepository.findUserById(id)
        if (todo == null){
            throw UserNotFoundException("User not found!")
        }else return todo
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun deleteById(id: Long): Boolean {
        return userRepository.deleteUserById(id)
    }

}