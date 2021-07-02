package org.nistagram.messagingmicroservice.controller.dto

import java.io.Serializable

data class ResponseDto(var success: Boolean = false, var message: String = "") : Serializable