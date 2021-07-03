package org.nistagram.messagingmicroservice.exception

class UserAlreadyExistsException : RuntimeException("User with the same username already exists.")

class UserDoesNotExistsException : RuntimeException("User with the same username does not exists.")