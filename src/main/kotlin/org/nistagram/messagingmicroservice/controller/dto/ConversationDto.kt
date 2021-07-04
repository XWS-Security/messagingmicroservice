package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class ConversationDto(var id: Long = 0, var status: String = "", var participants: List<String>) : Serializable