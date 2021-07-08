package org.nistagram.messagingmicroservice.controller

import org.nistagram.messagingmicroservice.controller.dto.*
import org.nistagram.messagingmicroservice.security.TokenUtils
import org.nistagram.messagingmicroservice.service.UserService
import org.nistagram.messagingmicroservice.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/users"])
@Validated
class UserController(private val userService: UserService, private val tokenUtils: TokenUtils) {
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
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
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

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    fun findUser(
        @PathVariable("username") username: String,
        request: HttpServletRequest
    ): ResponseEntity<FindUserResponse> {
        return try {
            val user = userService.find(username, tokenUtils.getToken(request) ?: throw AuthorizationException())
            val result = UserDto(user.username, profilePrivate = user.profilePrivate)
            ResponseEntity(FindUserResponse(user = result, success = true), HttpStatus.OK)
        } catch (e: UserDoesNotExistsException) {
            ResponseEntity(FindUserResponse(message = e.message ?: ""), HttpStatus.OK)
        } catch (e: UserCannotReceiveMessagesException) {
            ResponseEntity(FindUserResponse(message = e.message ?: ""), HttpStatus.OK)
        } catch (e: UserIsBlockedException) {
            ResponseEntity(FindUserResponse(message = e.message ?: ""), HttpStatus.OK)
        } catch (e: UserHasBlockedYouException) {
            ResponseEntity(FindUserResponse(message = e.message ?: ""), HttpStatus.OK)
        } catch (e: AuthorizationException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception) {
            ResponseEntity(FindUserResponse(message = "Something went wrong"), HttpStatus.BAD_REQUEST)
        }
    }
}