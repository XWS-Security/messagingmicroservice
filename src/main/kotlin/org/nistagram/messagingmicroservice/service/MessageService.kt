package org.nistagram.messagingmicroservice.service

import org.nistagram.messagingmicroservice.controller.dto.CreateContentMessageDto
import org.nistagram.messagingmicroservice.controller.dto.CreateOneTimeMessageDto
import org.nistagram.messagingmicroservice.controller.dto.CreateTextMessageDto
import org.nistagram.messagingmicroservice.controller.dto.MessageDto
import org.springframework.web.multipart.MultipartFile

interface MessageService {
    fun findByConversationId(conversationId: Long): List<MessageDto>
    fun sendTextMessage(dto: CreateTextMessageDto)
    fun sendContentMessage(dto: CreateContentMessageDto)
    fun sendOneTimeMessage(dto: CreateOneTimeMessageDto, files: List<MultipartFile>)
    fun setMessageStatus(messageId: Long)
}