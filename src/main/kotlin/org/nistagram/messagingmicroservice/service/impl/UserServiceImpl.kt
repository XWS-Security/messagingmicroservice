package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.controller.dto.UpdateUserDto
import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.data.repository.RoleRepository
import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.nistagram.messagingmicroservice.service.UserService
import org.nistagram.messagingmicroservice.util.UserAlreadyExistsException
import org.nistagram.messagingmicroservice.util.UserCannotReceiveMessagesException
import org.nistagram.messagingmicroservice.util.UserDoesNotExistsException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val roleRepository: RoleRepository) :
    UserService {

    override fun save(user: User) {
        if (userRepository.findByNistagramUsername(user.nistagramUsername) != null)
            throw UserAlreadyExistsException()
        user.roles = roleRepository.findByName(user.administrationRole)
        userRepository.save(user);
    }

    override fun delete(user: User) {
        if (userRepository.findByNistagramUsername(user.nistagramUsername) == null)
            throw UserDoesNotExistsException()
        userRepository.delete(user)
    }

    override fun update(userDto: UpdateUserDto) {
        if (userDto.oldUsername != userDto.username && userRepository.findByNistagramUsername(userDto.username) != null)
            throw UserAlreadyExistsException()
        val loadedUser = userRepository.findByNistagramUsername(userDto.oldUsername)
            ?: throw UserDoesNotExistsException()
        loadedUser.nistagramUsername = userDto.username
        loadedUser.profilePrivate = userDto.profilePrivate
        loadedUser.messagesEnabled = userDto.messagesEnabled
        userRepository.save(loadedUser)
    }

    override fun find(username: String): User {
        val user = userRepository.findByNistagramUsername(username) ?: throw UserDoesNotExistsException()
        // TODO: validate following status/blocked
        return if (user.messagesEnabled) user else throw UserCannotReceiveMessagesException()
    }
}