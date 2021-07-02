package org.nistagram.messagingmicroservice.data.model

import javax.persistence.*

@Entity
@Table(name = "AuthRole")
data class Role(
        @Id
        @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", initialValue = 100)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
        @Column(name = "id", unique = true)
        var id: Long = 0L,
        var name: String = "")