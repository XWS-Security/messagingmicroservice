package org.nistagram.messagingmicroservice.controller.dto

import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.util.Constants
import java.io.Serializable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CreateUserDto(var username: @NotNull @Pattern(regexp = Constants.USERNAME_PATTERN, message = Constants.USERNAME_INVALID_MESSAGE) String = "",
                         var profilePrivate: Boolean = false) : Serializable {
    fun toUser() = User(nistagramUsername = username, profilePrivate = profilePrivate)
}