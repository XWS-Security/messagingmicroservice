package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class CreateOneTimeMessageDto(var conversationId: Long = 0L, ): Serializable