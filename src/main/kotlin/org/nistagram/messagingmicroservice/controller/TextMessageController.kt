package org.nistagram.messagingmicroservice.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/textmessages"])
@Validated
class TextMessageController {
}