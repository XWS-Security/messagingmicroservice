package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.*
import org.nistagram.messagingmicroservice.service.FileDto
import org.nistagram.messagingmicroservice.service.FileService
import org.nistagram.messagingmicroservice.service.MessageService
import org.nistagram.messagingmicroservice.util.InvalidConversationException
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/messages"])
@Validated
class MessageController(private val messageService: MessageService, private val fileService: FileService) {
    @GetMapping("/{conversationId}")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun findByConversationId(@PathVariable("conversationId") conversationId: Long): ResponseEntity<List<MessageDto>> =
        try {
            ResponseEntity(messageService.findByConversationId(conversationId), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    @PostMapping("/text")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun sendTextMessage(@RequestBody message: CreateTextMessageDto): ResponseEntity<String> =
        try {
            messageService.sendTextMessage(message)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST)
        }

    @PostMapping("/content")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun sendContentMessage(@RequestBody message: CreateContentMessageDto): ResponseEntity<String> =
        try {
            messageService.sendContentMessage(message)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST)
        }

    @PostMapping("/oneTime", consumes = ["multipart/form-data"])
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun sendOneTimeMessage(
        @RequestPart("obj") message: CreateOneTimeMessageDto,
        @RequestPart("photos") files: List<MultipartFile>
    ): ResponseEntity<String> =
        try {
            messageService.sendOneTimeMessage(message, files)
            ResponseEntity(HttpStatus.OK)
        } catch (e: InvalidConversationException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST)
        }

    @GetMapping("/image/{file}")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun getImage(@PathVariable file: String): ResponseEntity<Resource> =
        try {
            val media: FileDto = fileService.get(file)
            val responseHeaders = HttpHeaders()
            responseHeaders["Content-Disposition"] = "attachment; filename=\"" + media.file.name + "\""
            responseHeaders["Content-Type"] = media.mediaType.toString()
            val type: MediaType = media.mediaType
            ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(media.file.length())
                .contentType(type)
                .body(media.stream)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    @PutMapping("/seen")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun setMessageStatus(@RequestBody dto: MessageSeenDto): ResponseEntity<Unit> =
        try {
            messageService.setMessageStatus(dto.messageId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
}