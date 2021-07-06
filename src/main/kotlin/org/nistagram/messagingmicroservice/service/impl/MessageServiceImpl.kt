package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.controller.dto.CreateContentMessageDto
import org.nistagram.messagingmicroservice.controller.dto.CreateTextMessageDto
import org.nistagram.messagingmicroservice.controller.dto.MessageDto
import org.nistagram.messagingmicroservice.controller.dto.MessageType
import org.nistagram.messagingmicroservice.data.model.*
import org.nistagram.messagingmicroservice.data.repository.ConversationRepository
import org.nistagram.messagingmicroservice.data.repository.MessageRepository
import org.nistagram.messagingmicroservice.service.MessageService
import org.nistagram.messagingmicroservice.util.InvalidConversationException
import org.nistagram.messagingmicroservice.util.UserDoesNotExistsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val conversationRepository: ConversationRepository
) : MessageService {
    override fun findByConversationId(conversationId: Long): List<MessageDto> =
        messageRepository.findByConversationId(conversationId)
            .map { message ->
                MessageDto(id = message.id, sentBy = message.sentBy.nistagramUsername).apply {
                    this.sentAt = formatDate(message.sentAt)
                    if (message is TextMessage) {
                        this.type = MessageType.TEXT
                        this.text = message.text
                    } else if (message is ContentMessage) {
                        this.type = MessageType.CONTENT
                        this.contentId = message.contentId
                    }
                    // TODO: add fields for other types
                }
            }

    override fun sendTextMessage(dto: CreateTextMessageDto) {
        val message = TextMessage(text = dto.text)
        saveMessage(message, dto.conversationId)
    }

    override fun sendContentMessage(dto: CreateContentMessageDto) {
        val message = ContentMessage(contentId = dto.contentId)
        saveMessage(message, dto.conversationId)
    }

    private fun saveMessage(message: Message, conversationId: Long) {
        message.sentBy = getCurrentUser()
        message.sentAt = Date()

        val conversation = getConversation(conversationId)
        messageRepository.save(message)
        conversation.messages.add(message)
        conversationRepository.save(conversation)
    }

    private fun getCurrentUser(): User {
        val obj = SecurityContextHolder.getContext().authentication.principal
        return if (obj is User) obj
        else throw UserDoesNotExistsException()
    }

    private fun getConversation(id: Long): Conversation {
        val optional = conversationRepository.findById(id)
        return if (optional.isPresent) optional.get() else throw InvalidConversationException()
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val timeFormat = SimpleDateFormat("HH:mm")
        val dateTimeFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
        val today = Date()
        return if (dateFormat.format(date) == dateFormat.format(today)) timeFormat.format(date)
        else dateTimeFormat.format(date)
    }
}