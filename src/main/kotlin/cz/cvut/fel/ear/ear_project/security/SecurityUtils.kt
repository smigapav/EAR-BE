package cz.cvut.fel.ear.ear_project.security

import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUtils {
    val currentUser: User?
        /**
         * Gets the currently authenticated user.
         *
         * @return Current user
         */
        get() {
            val ud = currentUserDetails
            return ud?.getUser()
        }
    val currentUserDetails: UserDetails?
        /**
         * Gets details of the currently authenticated user.
         *
         * @return Currently authenticated user details or null, if no one is currently authenticated
         */
        get() {
            val context: SecurityContext = SecurityContextHolder.getContext()
            return if (context.authentication != null && context.authentication.principal is UserDetails) {
                context.authentication.principal as UserDetails
            } else {
                null
            }
        }
    val isAuthenticatedAnonymously: Boolean
        /**
         * Checks whether the current authentication token represents an anonymous user.
         *
         * @return Whether current authentication is anonymous
         */
        get() = currentUserDetails == null
}
