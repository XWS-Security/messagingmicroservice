package org.nistagram.messagingmicroservice.data.repository

import org.nistagram.messagingmicroservice.data.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByNistagramUsername(username: String): User?
}