package org.nistagram.messagingmicroservice.controller.dto

data class MessageDto(
    var id: Long = 0,
    var sentBy: String = "",
    var sentAt: String = "",
    var type: MessageType = MessageType.TEXT,
    var text: String = "",
    var contentId: Long = 0L,
    var file: String = "",
    var seen: Boolean = false,
)