package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "group_transactions")
data class GroupTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_transaction_sequence")
    @SequenceGenerator(name = "group_transaction_sequence", sequenceName = "group_transaction_sequence", allocationSize = 1)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @field:NotNull(message = "Date must not be null")
    @Column(name = "transaction_date", nullable = false)
    val transactionDate: Long,

    @field:NotBlank(message = "Description must not be blank")
    @Column(name = "description", nullable = false)
    var description: String,

    @field:NotNull(message = "Amount paid must not be null")
    @Column(name = "amount_paid", nullable = false)
    var amountPaid: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    val paidByUser: User
)
