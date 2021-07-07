package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class MessageSeenDto(var messageId: Long = 0L) : Serializable