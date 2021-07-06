package org.nistagram.messagingmicroservice.data.repository

import org.nistagram.messagingmicroservice.data.model.Message
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId ORDER BY sent_at ASC", nativeQuery = true)
    fun findByConversationId(conversationId: Long): List<Message>
}