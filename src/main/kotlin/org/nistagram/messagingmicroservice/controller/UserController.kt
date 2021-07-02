package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.CreateUserDto
import org.nistagram.messagingmicroservice.controller.dto.ResponseDto
import org.nistagram.messagingmicroservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/users"])
class UserController(private val userService: UserService) {
    @PostMapping("/")
    fun createUser(@RequestBody userDto: CreateUserDto): ResponseEntity<ResponseDto> {
        return try {
            println("Creating user...")
            userService.save(userDto.toUser())
            println("User created.")
            ResponseEntity(ResponseDto(success = true), HttpStatus.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity(ResponseDto(message = "Something went wrong"), HttpStatus.OK)
        }
    }

    @PostMapping("/delete")
    fun deleteUser(@RequestBody userDto: CreateUserDto): ResponseEntity<ResponseDto> {
        return try {
            userService.delete(userDto.toUser())
            ResponseEntity(ResponseDto(true), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(ResponseDto(message = "Something went wrong"), HttpStatus.OK)
        }
    }
}