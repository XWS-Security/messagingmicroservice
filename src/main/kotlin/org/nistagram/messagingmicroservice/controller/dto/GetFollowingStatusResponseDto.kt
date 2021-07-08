package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class GetFollowingStatusResponseDto(
    var following: String = "",
    var notifications: String? = "",
    var muted: Boolean = false,
    var blocked: Boolean = false
) : Serializable