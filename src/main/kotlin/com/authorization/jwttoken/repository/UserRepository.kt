package com.authorization.jwttoken.repository

import com.authorization.jwttoken.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository
//    (    private val encoder: PasswordEncoder)
    : JpaRepository<User, Long> {

//    private val users = mutableListOf(
//        User(
//            id = 1,
//            email = "user@gmail.com",
//            password = encoder.encode("pass1")
//        ),
//        User(
//            id = 2,
//            email = "user1@gmail.com",
//            password = encoder.encode("pass1")
//        ),
//        User(
//            id = 3,
//            email = "user2@gmail.com",
//            password = encoder.encode("pass1")
//        ),
//        User(
//            id = 4,
//            email = "admin@gmail.com",
//            password = encoder.encode("pass")
//        )
//    )


//    fun saveUser(user: User): Boolean {
//        val updatedUser = user.copy(password = encoder.encode(user.password))
//        return users.add(updatedUser)
//    }


    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): User?
//        users.firstOrNull {
//            it.email == email
//        }

    //    fun findAll(): List<User> = users
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    fun findUserById(@Param("id") id: Long): User?
//    =
//        users.firstOrNull {
//            it.id == id
//        }

    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    fun deleteUserById(@Param("id") id: Long): Boolean
//        val user = findById(id)
//        return user.let {
//            users.remove(it)
//        }
//    }

}