package org.nistagram.messagingmicroservice.util

class UserAlreadyExistsException : RuntimeException("User with the same username already exists.")

class UserDoesNotExistsException : RuntimeException("User with the same username does not exists.")

class UserCannotReceiveMessagesException : RuntimeException("User has disabled messages. Try following them.")

class InvalidConversationException : RuntimeException("Conversation is not valid.")

class FileExtensionException : RuntimeException()

class CouldNotGetFileException : RuntimeException("Sorry, the requested file could not be loaded.")

class MessageNotFoundException : RuntimeException("Message does not exist.")