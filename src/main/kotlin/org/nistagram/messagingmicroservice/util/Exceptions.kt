package org.nistagram.messagingmicroservice.util

class UserAlreadyExistsException : RuntimeException("User with the same username already exists.")

class UserDoesNotExistsException : RuntimeException("User with the same username does not exists.")

class UserCannotReceiveMessages : RuntimeException("User has disabled messages. Try following them.")