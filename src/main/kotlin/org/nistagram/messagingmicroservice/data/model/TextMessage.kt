package org.nistagram.messagingmicroservice.data.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("TEXT_MESSAGE")
data class TextMessage(var text: String) : Message()
