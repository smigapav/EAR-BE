package cz.cvut.kbss.ear.eshop.config

import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.kbss.ear.eshop.security.AuthenticationFailure
import cz.cvut.kbss.ear.eshop.security.AuthenticationSuccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity // Allows Spring Security
@EnableMethodSecurity // Allow methods to be secured using annotation @PreAuthorize and @PostAuthorize
@Profile("!test")
class SecurityConfig() {

    private val objectMapper = ObjectMapper()
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authSuccess = authenticationSuccess()
        http {
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            exceptionHandling {
                authenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            }
            csrf { disable() }
            cors { configurationSource = corsConfigurationSource() }
            headers { frameOptions { sameOrigin = true } }
            formLogin {
                authenticationSuccessHandler = authSuccess
                authenticationFailureHandler = authenticationFailureHandler()
            }
            logout {
                logoutSuccessHandler = authSuccess
            }
        }
        return http.build()
    }
    private fun authenticationFailureHandler(): AuthenticationFailure {
        return AuthenticationFailure(objectMapper)
    }

    private fun authenticationSuccess(): AuthenticationSuccess {
        return AuthenticationSuccess(objectMapper)
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        // We're allowing all methods from all origins
        // so that the application API is usable also by other clients
        // than just the UI.
        // This behavior can be restricted later.
        val configuration = CorsConfiguration()
        // AllowCredentials requires a particular origin configured, * is rejected by the browser
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.addExposedHeader(HttpHeaders.LOCATION)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
