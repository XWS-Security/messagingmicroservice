package org.nistagram.messagingmicroservice.service

import org.nistagram.messagingmicroservice.controller.dto.CreateContentMessageDto
import org.nistagram.messagingmicroservice.controller.dto.CreateTextMessageDto
import org.nistagram.messagingmicroservice.controller.dto.MessageDto

interface MessageService {
    fun findByConversationId(conversationId: Long): List<MessageDto>
    fun sendTextMessage(dto: CreateTextMessageDto)
    fun sendContentMessage(dto: CreateContentMessageDto)
}