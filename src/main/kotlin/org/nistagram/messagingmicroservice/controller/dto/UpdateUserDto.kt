package org.nistagram.messagingmicroservice.controller.dto

import org.nistagram.messagingmicroservice.util.Constants
import java.io.Serializable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UpdateUserDto(var username: @NotNull @Pattern(regexp = Constants.USERNAME_PATTERN, message = Constants.USERNAME_INVALID_MESSAGE) String = "",
                         var oldUsername: @NotNull @Pattern(regexp = Constants.USERNAME_PATTERN, message = Constants.USERNAME_INVALID_MESSAGE) String = "",
                         var profilePrivate: Boolean = false,
                         var messagesEnabled: Boolean = true) : Serializable