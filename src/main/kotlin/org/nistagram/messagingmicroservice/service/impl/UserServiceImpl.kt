package org.nistagram.messagingmicroservice.service.impl

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.nistagram.messagingmicroservice.controller.dto.GetFollowingStatusResponseDto
import org.nistagram.messagingmicroservice.controller.dto.UpdateUserDto
import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.data.repository.RoleRepository
import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.nistagram.messagingmicroservice.service.UserService
import org.nistagram.messagingmicroservice.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val roleRepository: RoleRepository) :
    UserService {
    @Value("\${FOLLOWER}")
    private val followerMicroserviceURI: String = ""

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

    override fun find(username: String, token: String): User {
        val user = userRepository.findByNistagramUsername(username) ?: throw UserDoesNotExistsException()
        val followingStatus = getFollowingStatus(username, token)
        return when {
            user.messagesEnabled && followingStatus == "NOT_FOLLOWING" -> user
            followingStatus == "FOLLOWING" -> user
            followingStatus == "USER_BLOCKED" -> throw UserIsBlockedException()
            followingStatus == "BLOCKED" -> throw UserHasBlockedYouException()
            else -> throw UserCannotReceiveMessagesException()
        }
    }

    private fun getFollowingStatus(participant: String, token: String): String {
        // SSL
        val sslContext: SslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build()
        val httpClient: HttpClient = HttpClient.create().secure { t -> t.sslContext(sslContext) }

        // Creating web client.
        val client: WebClient = WebClient.builder()
            .baseUrl(followerMicroserviceURI)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()

        // Define a method.
        val result = client.get()
            .uri("/interactions/reverse/$participant")
            .headers { h -> h.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(GetFollowingStatusResponseDto::class.java)
            .block()

        return result?.following ?: ""
    }
}