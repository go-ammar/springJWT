package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository
    : JpaRepository<User, Long> {


    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): User?

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    fun findUserById(@Param("id") id: Long): User?

    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    fun deleteUserById(@Param("id") id: Long): Boolean

    @Modifying
    @Query(value = "UPDATE users u SET u.name = :name, u.dob = :dob WHERE u.id = :id", nativeQuery = true)
    fun updateUser(
        @Param("id") id: Long,
        @Param("name") name: String?,
        @Param("dob") dob: Long,
    )

}