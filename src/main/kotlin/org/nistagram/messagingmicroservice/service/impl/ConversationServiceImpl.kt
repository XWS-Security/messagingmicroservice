package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.controller.dto.ConversationDto
import org.nistagram.messagingmicroservice.data.model.Conversation
import org.nistagram.messagingmicroservice.data.model.ConversationStatus
import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.data.model.enum.ApprovalStatus
import org.nistagram.messagingmicroservice.data.repository.ConversationRepository
import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.nistagram.messagingmicroservice.service.ConversationService
import org.nistagram.messagingmicroservice.util.UserDoesNotExistsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ConversationServiceImpl(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository
) : ConversationService {
    override fun getAll(): List<ConversationDto> =
        conversationRepository.findByParticipant(getCurrentUser().id).map { r ->
            ConversationDto(id = r.id,
                status = r.participants[getCurrentUser()]!!.status.name,
                participants = r.participants.map { entry -> entry.key.nistagramUsername })
        }

    override fun start(participants: List<String>): Boolean {
        var areAllUsersValid = true
        val currentUser = getCurrentUser()
        // TODO: validate following status in follower microservice

        val conversation = Conversation()
        conversation.participants[currentUser] = ConversationStatus(ApprovalStatus.INITIATED)
        participants.forEach { username ->
            val user = userRepository.findByNistagramUsername(username)
            if (user == null) areAllUsersValid = false
            else conversation.participants[user] =
                if (user.profilePrivate) ConversationStatus(ApprovalStatus.PENDING)
                else ConversationStatus(ApprovalStatus.ACCEPTED)
        }
        conversationRepository.save(conversation)
        return areAllUsersValid
    }

    private fun getCurrentUser(): User {
        val obj = SecurityContextHolder.getContext().authentication.principal
        return if (obj is User) obj
        else throw UserDoesNotExistsException()
    }
}