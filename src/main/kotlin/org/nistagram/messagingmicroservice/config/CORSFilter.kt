package org.nistagram.messagingmicroservice.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CORSFilter : Filter {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val response = servletResponse as HttpServletResponse
        val request = servletRequest as HttpServletRequest
        response.setHeader("Access-Control-Allow-Origin", "*") // NOSONAR

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "access_token, authorization, content-type")

        if ("OPTIONS" == request.method) {
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain?.doFilter(servletRequest, servletResponse)
        }
    }
}