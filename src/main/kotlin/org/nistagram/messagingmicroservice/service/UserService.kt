package org.nistagram.messagingmicroservice.service

import org.nistagram.messagingmicroservice.data.model.User

interface UserService {
    fun save(user: User)
    fun delete(user: User)
}