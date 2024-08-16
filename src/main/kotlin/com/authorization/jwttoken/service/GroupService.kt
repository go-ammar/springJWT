package com.authorization.jwttoken.service

import com.authorization.jwttoken.model.Group
import com.authorization.jwttoken.model.GroupUser
import com.authorization.jwttoken.repository.GroupRepository
import com.authorization.jwttoken.repository.GroupUserRepository
import com.authorization.jwttoken.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val groupUserRepository: GroupUserRepository
) {

    @Transactional
    fun createGroup(name: String, createdByUserId: Long, userIds: List<Long>): Group {
        val createdByUser = userRepository.findById(createdByUserId)
            .orElseThrow { RuntimeException("User not found") }

        // Create the group
        val group = Group(
            name = name,
            createdBy = createdByUser.email // Assuming the `createdBy` field stores the email of the creator
        )
        val savedGroup = groupRepository.save(group)

        // Add users to the group, including the creator
        val users = userIds.map { userId ->
            userRepository.findById(userId)
                .orElseThrow { RuntimeException("User not found with ID: $userId") }
        } + createdByUser // Ensure the creator is also added to the group

        users.forEach { user ->
            val groupUser = GroupUser(
                group = savedGroup,
                user = user
            )
            groupUserRepository.save(groupUser)
        }

        return savedGroup
    }

}