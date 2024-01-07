package cz.cvut.fel.ear.ear_project.config

import CustomPermissionEvaluator
import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.ear.ear_project.security.AuthenticationFailure
import cz.cvut.fel.ear.ear_project.security.AuthenticationSuccess
import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    @Autowired
    private val projectService: ProjectService,
    @Autowired
    private val storyService: StoryService,
) : GlobalMethodSecurityConfiguration() {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authSuccess = authenticationSuccess()

        http {
            authorizeHttpRequests {
                authorize("/login", permitAll)
                authorize("/user/register", permitAll)
                authorize(anyRequest, authenticated)
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

    override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(CustomPermissionEvaluator(projectService))
        return expressionHandler
    }
}
