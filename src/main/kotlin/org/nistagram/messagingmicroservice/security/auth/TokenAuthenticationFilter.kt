package org.nistagram.messagingmicroservice.security.auth

import org.nistagram.messagingmicroservice.security.TokenUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class TokenAuthenticationFilter(private val tokenUtils: TokenUtils, private val userDetailsService: UserDetailsService) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authToken = tokenUtils.getToken(request)
        if (authToken != null) {
            val username: String? = tokenUtils.getUsernameFromToken(authToken)
            if (username != null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                if (tokenUtils.validateToken(authToken, userDetails) == true) {
                    val authentication = TokenBasedAuthentication(userDetails)
                    authentication.token = authToken
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}