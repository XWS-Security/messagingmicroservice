package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.data.repository.RoleRepository
import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.nistagram.messagingmicroservice.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val roleRepository: RoleRepository)
    : UserService {

    override fun save(user: User) {
        // TODO: validate
        user.roles = roleRepository.findByName(user.administrationRole)
        userRepository.save(user);
    }

    override fun delete(user: User) {
        // TODO: validate
        userRepository.delete(user)
    }
}