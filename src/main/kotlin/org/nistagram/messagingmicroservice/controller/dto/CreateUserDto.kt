package org.nistagram.messagingmicroservice.controller.dto

import org.nistagram.messagingmicroservice.data.model.User
import java.io.Serializable

data class CreateUserDto(var username: String = "",
                         var profilePrivate: Boolean = false) : Serializable {
    fun toUser() = User(nistagramUsername = username, profilePrivate = profilePrivate)
}