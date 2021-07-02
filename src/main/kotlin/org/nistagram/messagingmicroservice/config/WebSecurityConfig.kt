package org.nistagram.messagingmicroservice.config

import org.nistagram.messagingmicroservice.security.TokenUtils
import org.nistagram.messagingmicroservice.security.auth.RestAuthenticationEntryPoint
import org.nistagram.messagingmicroservice.security.auth.TokenAuthenticationFilter
import org.nistagram.messagingmicroservice.service.impl.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import kotlin.jvm.Throws

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(private val jwtUserDetailsService: CustomUserDetailsService,
                        private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
                        private val tokenUtils: TokenUtils) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(jwtUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors().and()
                .csrf().disable()
                // Set session management to stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Set unauthorized requests exception handler
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                // Set permissions on endpoints
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated().and()
                // Add JWT token filter
                .addFilterBefore(TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
                        BasicAuthenticationFilter::class.java)
        // Enables SSL
        http.requiresChannel().anyRequest().requiresSecure()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js")
    }
}