package com.authorization.jwttoken.model

import jakarta.persistence.*

@Entity
@Table(name = "group_users")
data class GroupUser(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_user_sequence")
    @SequenceGenerator(name = "group_user_sequence", sequenceName = "group_user_sequence", allocationSize = 1)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)