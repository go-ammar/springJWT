package com.authorization.jwttoken.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_sequence")
    @SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence", allocationSize = 1)
    val id: Long = 0,

    @field:NotBlank(message = "Group name must not be blank")
    @Column(name = "name", nullable = false)
    var name: String,

    @field:NotBlank(message = "Created by user must not be blank")
    @Column(name = "created_by", nullable = false)
    val createdBy: String // This would be the userId of the user who created the group
)