package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class FindUserResponse(var user: UserDto = UserDto(), var message: String = "", var success: Boolean = false) :
    Serializable