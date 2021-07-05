package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.ConversationDto
import org.nistagram.messagingmicroservice.service.ConversationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/conversations"])
@Validated
class ConversationController(private val conversationService: ConversationService) {
    @GetMapping("/")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun getAll(): ResponseEntity<List<ConversationDto>> {
        return try {
            ResponseEntity(conversationService.getAll(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun start(@RequestBody participants: List<String>): ResponseEntity<String> {
        return try {
            conversationService.start(participants)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong!", HttpStatus.BAD_REQUEST)
        }
    }
}