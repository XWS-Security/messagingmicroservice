package org.nistagram.messagingmicroservice.data.model

import org.nistagram.messagingmicroservice.data.model.enum.ApprovalStatus
import javax.persistence.Embeddable

@Embeddable
data class ConversationStatus(var status: ApprovalStatus)