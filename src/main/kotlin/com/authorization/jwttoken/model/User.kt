package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "id_generator", allocationSize = 1)
    val id: Long = 0,

    @field:NotBlank(message = "Category must not be blank")
    @Column(name = "password", nullable = false)
    var password: String,

    @field:NotBlank(message = "Month must not be blank")
    @Column(name = "email", nullable = false, unique = true)
    val email: String
)