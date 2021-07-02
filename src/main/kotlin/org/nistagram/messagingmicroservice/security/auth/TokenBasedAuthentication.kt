package org.nistagram.messagingmicroservice.security.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails

class TokenBasedAuthentication(private val principle: UserDetails) : AbstractAuthenticationToken(principle.authorities) {
    var token: String? = null

    override fun isAuthenticated() = true

    override fun getCredentials(): Any? = token

    override fun getPrincipal() = principle
}