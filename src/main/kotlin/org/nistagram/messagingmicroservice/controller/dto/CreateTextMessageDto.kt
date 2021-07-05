package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class CreateTextMessageDto(var text: String = "", var conversationId: Long = 0L) : Serializable