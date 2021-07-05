package org.nistagram.messagingmicroservice.controller.dto

data class MessageDto(
    var id: Long = 0,
    var sentBy: String = "",
    var read: Boolean = false,
    var type: MessageType = MessageType.TEXT,
    var text: String = ""
)