package org.nistagram.messagingmicroservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenUtils {
    @Value("\${SECRET}")
    var SECRET: String? = null

    @Value("Authorization")
    private val AUTH_HEADER: String? = null

    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username = getUsernameFromToken(token)
        return username != null && username == userDetails.username
    }

    fun getUsernameFromToken(token: String?): String? = try {
        val claims = getAllClaimsFromToken(token)
        claims!!.subject
    } catch (e: Exception) {
        null
    }

    fun getIssuedAtDateFromToken(token: String?): Date? {
        val issueAt: Date?
        issueAt = try {
            val claims = getAllClaimsFromToken(token)
            claims!!.issuedAt
        } catch (e: Exception) {
            null
        }
        return issueAt
    }

    fun getToken(request: HttpServletRequest): String? {
        val authHeader = getAuthHeaderFromHeader(request)
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else null
    }

    fun getAuthHeaderFromHeader(request: HttpServletRequest): String? {
        return request.getHeader(AUTH_HEADER)
    }

    private fun isCreatedBeforeLastPasswordReset(created: Date?, lastPasswordReset: Date?): Boolean? {
        return lastPasswordReset != null && created!!.before(lastPasswordReset)
    }

    private fun getAllClaimsFromToken(token: String?): Claims? = try {
        Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
    } catch (e: Exception) {
        null
    }
}