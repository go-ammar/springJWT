package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "group_transaction_user_shares")
data class GroupTransactionUserShare(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_transaction_user_share_sequence")
    @SequenceGenerator(name = "group_transaction_user_share_sequence", sequenceName = "group_transaction_user_share_sequence", allocationSize = 1)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_transaction_id", nullable = false)
    val groupTransaction: GroupTransaction,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @field:NotNull(message = "Share amount must not be null")
    @Column(name = "share_amount", nullable = false)
    var shareAmount: Int
)
