package org.nistagram.messagingmicroservice.data.repository

import org.nistagram.messagingmicroservice.data.model.Conversation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ConversationRepository : CrudRepository<Conversation, Long> {
    @Query("SELECT conv.id FROM conversation AS conv, conversation_user AS cu " +
                "WHERE cu.conversation_id = conv.id AND cu.user_id = :userId", nativeQuery = true)
    fun findByParticipant(userId: Long): List<Conversation>
}