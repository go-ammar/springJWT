package com.authorization.jwttoken.service

import com.authorization.jwttoken.controller.user.UpdateUserRequest
import com.authorization.jwttoken.controller.user.UserResponse
import com.authorization.jwttoken.exceptions.UserExistsException
import com.authorization.jwttoken.exceptions.UserNotFoundException
import com.authorization.jwttoken.model.User
import com.authorization.jwttoken.repository.UserRepository
import com.authorization.jwttoken.util.JwtUtil
import com.google.gson.Gson
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {


    @Transactional
    fun saveUser(user: User): User? {
        // Encode the password before saving
        val userFound = userRepository.findByEmail(user.email)
        if (userFound == null) {
            user.password = passwordEncoder.encode(user.password)
//            user.name = user.name.replace(" ", "_")
            return userRepository.save(user)
        } else {
            throw UserExistsException(user.email)
        }
    }

    @Transactional
    fun updateUser(user: UpdateUserRequest, token: String): UserResponse? {
        // Encode the password before saving
        val userId = jwtUtil.extractUser(token)!!.id.toString()
         val userFound = userRepository.findByEmail(user.email)

        if (userFound != null) {
//            user.name = user.name.replace(" ", "_")

            userRepository.updateUser(id = userId.toLong(), name = user.name, dob = user.dob)
            val map: MutableMap<String, String> = mutableMapOf()
            map["user"] = Gson().toJson(
                User(
                    email = userFound.email,
                    password = userFound.password,
                    id = userFound.id,
                    name = userFound.name,
                    dob = userFound.dob
                )
            )
            val updatedToken = jwtUtil.updateClaims(token, map)

            return UserResponse(
                id = userId.toLong(),
                email = user.email,
                name = user.name,
                dob = user.dob,
                updatedToken = updatedToken
            )
        } else {
            throw UserExistsException(user.email)
        }
    }


    fun findById(id: Long): User {
        val user = userRepository.findUserById(id)
        if (user == null) {
            throw UserNotFoundException("User not found!")
        } else return user
    }

    fun findAll(): List<User> {
//         userRepository.findAll().map {
//            it.name = it.name.replace("_", " ")
//        }
        return userRepository.findAll()
    }   

    fun deleteById(id: Long): Boolean {
        return userRepository.deleteUserById(id)
    }

}