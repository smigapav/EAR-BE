package cz.cvut.kbss.ear.eshop.security

import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.kbss.ear.eshop.security.model.LoginStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.io.IOException

class AuthenticationFailure(private val mapper: ObjectMapper) : AuthenticationFailureHandler {
    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        val status = LoginStatus(false, false, null, e.message)
        mapper.writeValue(httpServletResponse.outputStream, status)
    }
}
