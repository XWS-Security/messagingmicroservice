package org.nistagram.messagingmicroservice.service.impl

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.nistagram.messagingmicroservice.controller.dto.ConversationDto
import org.nistagram.messagingmicroservice.controller.dto.GetFollowingStatusResponseDto
import org.nistagram.messagingmicroservice.data.model.Conversation
import org.nistagram.messagingmicroservice.data.model.ConversationStatus
import org.nistagram.messagingmicroservice.data.model.User
import org.nistagram.messagingmicroservice.data.model.enum.ApprovalStatus
import org.nistagram.messagingmicroservice.data.repository.ConversationRepository
import org.nistagram.messagingmicroservice.data.repository.UserRepository
import org.nistagram.messagingmicroservice.service.ConversationService
import org.nistagram.messagingmicroservice.util.UserDoesNotExistsException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Service
class ConversationServiceImpl(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository
) : ConversationService {
    @Value("\${FOLLOWER}")
    private val followerMicroserviceURI: String = ""

    override fun getAll(): List<ConversationDto> =
        conversationRepository.findByParticipant(getCurrentUser().id).map { r ->
            ConversationDto(id = r.id,
                status = r.participants[getCurrentUser()]!!.status.name,
                participants = r.participants.map { entry -> entry.key.nistagramUsername })
        }

    override fun start(participants: List<String>, token: String): Boolean {
        var areAllUsersValid = true
        val currentUser = getCurrentUser()

        val conversation = Conversation()
        conversation.participants[currentUser] = ConversationStatus(ApprovalStatus.INITIATED)
        participants.forEach { username ->
            val followingStatus = getFollowingStatus(username, token)
            val user = userRepository.findByNistagramUsername(username)
            if (user == null || followingStatus == "BLOCKED") areAllUsersValid = false
            else conversation.participants[user] =
                if (!user.profilePrivate || followingStatus == "FOLLOWING") ConversationStatus(ApprovalStatus.ACCEPTED)
                else ConversationStatus(ApprovalStatus.PENDING)
        }
        conversationRepository.save(conversation)
        return areAllUsersValid
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

    private fun getCurrentUser(): User {
        val obj = SecurityContextHolder.getContext().authentication.principal
        return if (obj is User) obj
        else throw UserDoesNotExistsException()
    }
}