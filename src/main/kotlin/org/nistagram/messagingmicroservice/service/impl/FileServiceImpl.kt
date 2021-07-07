package org.nistagram.messagingmicroservice.service.impl

import org.nistagram.messagingmicroservice.service.FileDto
import org.nistagram.messagingmicroservice.service.FileService
import org.nistagram.messagingmicroservice.util.CouldNotGetFileException
import org.nistagram.messagingmicroservice.util.FileExtensionException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO

@Service
class FileServiceImpl : FileService {
    @Value("\${IMAGES_PATH}")
    private val imagesPath: String = ""

    override fun save(file: MultipartFile): String =
        try {
            val folderPath: Path = Paths.get(imagesPath)
            val ext = file.originalFilename?.split(".")?.get(1) ?: throw FileExtensionException()
            val name = UUID.randomUUID().toString() + "." + ext
            Files.copy(file.inputStream, folderPath.resolve(name))
            name
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    override fun get(name: String): FileDto {
        val file = File(imagesPath + name)
        return when {
            isImageFile(file) -> {
                val stream = InputStreamResource(FileInputStream(file))
                FileDto(file, MediaType.IMAGE_JPEG, stream)
            }
            isVideoFile(file) -> {
                val stream = InputStreamResource(FileInputStream(file))
                FileDto(file, MediaType.APPLICATION_OCTET_STREAM, stream)
            }
            else -> {
                throw CouldNotGetFileException()
            }
        }
    }

    private fun isImageFile(file: File) = ImageIO.read(file) != null
    private fun isVideoFile(file: File): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(file.path)
        return mimeType != null && mimeType.startsWith("video")
    }
}