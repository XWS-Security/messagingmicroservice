package org.nistagram.messagingmicroservice.service

import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileService {
    fun save(file: MultipartFile): String
    fun get(name: String): FileDto
}

data class FileDto(var file: File, var mediaType: MediaType, var stream: InputStreamResource)