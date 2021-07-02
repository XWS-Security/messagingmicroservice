package org.nistagram.messagingmicroservice.data.repository

import org.nistagram.messagingmicroservice.data.model.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
    fun findByName(name: String): List<Role>
}