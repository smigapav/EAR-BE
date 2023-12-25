package cz.cvut.fel.ear.ear_project.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import java.io.IOException

class AuthenticationSuccess(private val mapper: ObjectMapper) : AuthenticationSuccessHandler, LogoutSuccessHandler {
    @Throws(IOException::class)
    override fun onAuthenticationSuccess(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication,
    ) {
        val username = getUsername(authentication)
        val loginStatus = LoginStatus(true, authentication.isAuthenticated, username, null)
        mapper.writeValue(httpServletResponse.outputStream, loginStatus)
    }

    private fun getUsername(authentication: Authentication?): String {
        return if (authentication == null) {
            ""
        } else {
            (authentication.principal as UserDetails).username
        }
    }

    @Throws(IOException::class)
    override fun onLogoutSuccess(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication,
    ) {
        val loginStatus = LoginStatus(false, true, null, null)
        mapper.writeValue(httpServletResponse.outputStream, loginStatus)
    }
}
