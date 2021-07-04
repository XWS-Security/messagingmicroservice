package org.nistagram.messagingmicroservice.data.model

import javax.persistence.*

@Entity
@Table(name = "Conversation")
data class Conversation(
    @Id
    @SequenceGenerator(
        name = "conversation_sequence_generator",
        sequenceName = "conversation_sequence",
        initialValue = 100
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversation_sequence_generator")
    @Column(name = "id", unique = true)
    var id: Long = 0L,

    @ElementCollection
    @JoinTable(
        name = "conversation_user",
        joinColumns = [JoinColumn(name = "conversation_id", referencedColumnName = "id")]
    )
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "status")
    var participants: MutableMap<User, ConversationStatus> = mutableMapOf(),

    @OneToMany(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "conversation_id")
    var messages: MutableList<Message> = mutableListOf()
)