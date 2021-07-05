package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.CreateTextMessageDto
import org.nistagram.messagingmicroservice.controller.dto.MessageDto
import org.nistagram.messagingmicroservice.service.MessageService
import org.nistagram.messagingmicroservice.util.InvalidConversationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/messages"])
@Validated
class MessageController(private val messageService: MessageService) {
    @GetMapping("/{conversationId}")
    fun findByConversationId(@PathVariable("conversationId") conversationId: Long): ResponseEntity<List<MessageDto>> =
        try {
            ResponseEntity(messageService.findByConversationId(conversationId), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    @PostMapping("/text")
    fun sendTextMessage(@RequestBody message: CreateTextMessageDto): ResponseEntity<String> =
        try {
            messageService.sendTextMessage(message)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST)
        }
}