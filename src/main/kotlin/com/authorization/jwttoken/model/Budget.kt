package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "budget")
data class Budget(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_sequence")
    @SequenceGenerator(name = "budget_sequence", sequenceName = "budget_sequence", allocationSize = 1)
    val id: Long = 0,

    @field:NotBlank(message = "Category must be Unique")
    @Column(name = "category", nullable = false, unique = true)
    var category: String,

    @field:NotNull(message = "Amount must not be null")
    @Column(name = "amount", nullable = false)
    var amount: Int,

    @field:NotNull(message = "UserId must not be null")
    @Column(name = "userId", nullable = false)
    val userId: String,

    )