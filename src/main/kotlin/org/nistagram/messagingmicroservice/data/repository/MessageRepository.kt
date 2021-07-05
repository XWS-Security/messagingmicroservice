package org.nistagram.messagingmicroservice.data.repository

import org.nistagram.messagingmicroservice.data.model.Message
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
    @Query("SELECT message_type, id, read, content_id, text, user_id, conversation_id FROM message " +
            "WHERE conversation_id = :conversationId", nativeQuery = true)
    fun findByConversationId(conversationId: Long): List<Message>
}