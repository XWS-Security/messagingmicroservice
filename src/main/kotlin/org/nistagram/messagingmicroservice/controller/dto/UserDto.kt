package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class UserDto(var username: String = "", var profilePrivate: Boolean = false) : Serializable