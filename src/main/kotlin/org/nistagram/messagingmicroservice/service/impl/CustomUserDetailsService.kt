package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByNistagramUsername(username)
            ?: throw UsernameNotFoundException(String.format("No user found with username '%s'.", username))
}