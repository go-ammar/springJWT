package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "transactions")
data class TransactionHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
    val id: Long = 0,

    @field:NotBlank(message = "Category must not be blank")
    @Column(name = "category", nullable = false)
    var category: String,

    @field:NotNull(message = "Date needs to set")
    @Column(name = "transaction_date", nullable = false, unique = false)
    val transactionDate: Long,

    @field:NotNull(message = "Amount must not be null")
    @Column(name = "amount", nullable = false)
    var amount: Int,

    @field:NotNull(message = "UserId must not be null")
    @Column(name = "user_id", nullable = false)
    val userId: String,
)