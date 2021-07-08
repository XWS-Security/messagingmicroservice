package org.nistagram.messagingmicroservice.service

import org.nistagram.messagingmicroservice.controller.dto.ConversationDto

interface ConversationService {
    fun getAll(): List<ConversationDto>
    fun start(participants: List<String>, token: String): Boolean
    fun accept(id: Long)
    fun reject(id: Long)
    fun delete(id: Long)
}