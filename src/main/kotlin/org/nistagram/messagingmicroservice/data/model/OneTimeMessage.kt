package org.nistagram.messagingmicroservice.data.model

import javax.persistence.*

@Entity
@DiscriminatorValue("ONE_TIME_MESSAGE")
data class OneTimeMessage(
    var file: String = "",
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "message_seen",
        joinColumns = [JoinColumn(name = "message_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var seenBy: MutableList<User> = mutableListOf()
) : Message()