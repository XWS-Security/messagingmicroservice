package org.nistagram.messagingmicroservice.util

class Constants {
    companion object {
        const val USERNAME_PATTERN = "^[a-zA-Z0-9_]{2,12}"
        const val USERNAME_INVALID_MESSAGE = "Username must be 2 to 12 characters long and can contain only letters, numbers and an underscore."
    }
}