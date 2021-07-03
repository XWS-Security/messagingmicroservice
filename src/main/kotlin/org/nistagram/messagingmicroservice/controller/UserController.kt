package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.CreateUserDto
import org.nistagram.messagingmicroservice.controller.dto.ResponseDto
import org.nistagram.messagingmicroservice.controller.dto.UpdateUserDto
import org.nistagram.messagingmicroservice.exception.UserAlreadyExistsException
import org.nistagram.messagingmicroservice.exception.UserDoesNotExistsException
import org.nistagram.messagingmicroservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/users"])
@Validated
class UserController(private val userService: UserService) {
    @PostMapping("/")
    fun createUser(@RequestBody userDto: CreateUserDto): ResponseEntity<ResponseDto> {
        return try {
            userService.save(userDto.toUser())
            ResponseEntity(ResponseDto(success = true), HttpStatus.OK)
        } catch (e: UserAlreadyExistsException) {
            ResponseEntity(ResponseDto(message = e.message ?: "Something went wrong"), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(ResponseDto(message = "Something went wrong"), HttpStatus.OK)
        }
    }

    @PostMapping("/delete")
    fun deleteUser(@RequestBody userDto: CreateUserDto): ResponseEntity<ResponseDto> {
        return try {
            userService.delete(userDto.toUser())
            ResponseEntity(ResponseDto(true), HttpStatus.OK)
        } catch (e: UserDoesNotExistsException) {
            ResponseEntity(ResponseDto(message = e.message ?: "Something went wrong"), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(ResponseDto(message = "Something went wrong"), HttpStatus.OK)
        }
    }

    @PutMapping("/")
    fun updateUser(@RequestBody userDto: UpdateUserDto): ResponseEntity<ResponseDto> {
        return try {
            userService.update(userDto)
            ResponseEntity(ResponseDto(success = true), HttpStatus.OK)
        } catch (e: UserDoesNotExistsException) {
            ResponseEntity(ResponseDto(message = e.message ?: "Something went wrong"), HttpStatus.OK)
        } catch (e: UserAlreadyExistsException) {
            ResponseEntity(ResponseDto(message = e.message ?: "Something went wrong"), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(ResponseDto(message = "Something went wrong"), HttpStatus.OK)
        }
    }
}