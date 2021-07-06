package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class CreateContentMessageDto(var contentId: Long = 0L, var conversationId: Long = 0L) : Serializable