package org.nistagram.messagingmicroservice.data.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("CONTENT_MESSAGE")
data class ContentMessage(var contentId: Long) : Message()