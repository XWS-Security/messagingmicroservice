package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.ConversationDto
import org.nistagram.messagingmicroservice.controller.dto.UpdateConversationStatusDto
import org.nistagram.messagingmicroservice.security.TokenUtils
import org.nistagram.messagingmicroservice.service.ConversationService
import org.nistagram.messagingmicroservice.util.AuthorizationException
import org.nistagram.messagingmicroservice.util.InvalidConversationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/conversations"])
@Validated
class ConversationController(private val conversationService: ConversationService, private val tokenUtils: TokenUtils) {
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
    fun start(@RequestBody participants: List<String>, request: HttpServletRequest): ResponseEntity<String> {
        return try {
            conversationService.start(participants, tokenUtils.getToken(request) ?: throw AuthorizationException())
            ResponseEntity(HttpStatus.OK)
        } catch (e: AuthorizationException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong!", HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/accept")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun accept(@RequestBody dto: UpdateConversationStatusDto): ResponseEntity<String> {
        return try {
            conversationService.accept(dto.conversationId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message ?: "", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong!", HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/reject")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun reject(@RequestBody dto: UpdateConversationStatusDto): ResponseEntity<String> {
        return try {
            conversationService.reject(dto.conversationId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message ?: "", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong!", HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/delete")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun delete(@RequestBody dto: UpdateConversationStatusDto): ResponseEntity<String> {
        return try {
            conversationService.delete(dto.conversationId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message ?: "", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong!", HttpStatus.BAD_REQUEST)
        }
    }
}