package org.nistagram.messagingmicroservice.data.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type", discriminatorType = DiscriminatorType.STRING)
abstract class Message(
    @Id
    @SequenceGenerator(name = "message_sequence_generator", sequenceName = "message_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence_generator")
    @Column(name = "id", unique = true)
    var id: Long = 0L,
    var sentAt: Date = Date(),
    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user_id")
    var sentBy: User = User()
)