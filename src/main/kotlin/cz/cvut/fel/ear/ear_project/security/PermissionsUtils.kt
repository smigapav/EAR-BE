package cz.cvut.fel.ear.ear_project.security

import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class PermissionsUtils {
    companion object {
        fun updateUserPermissions(user: User) {
            val authorities: MutableSet<GrantedAuthority> = HashSet()
            addUserRoles(user, authorities)
            val userDetails = UserDetails(user, authorities)
            val authentication = SecurityContextHolder.getContext().authentication
            val newAuthentication = UsernamePasswordAuthenticationToken(userDetails, authentication.credentials, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = newAuthentication
        }

        fun addUserRoles(
            user: User,
            authorities: MutableSet<GrantedAuthority>,
        ) {
            user.permissions.forEach { permissions ->
                if (permissions.projectAdmin) {
                    val authority = "${permissions.project?.id!!}admin"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
                if (permissions.storiesAndTasksManager) {
                    val authority = "${permissions.project?.id!!}manager"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
                if (permissions.canManageSprints) {
                    val authority = "${permissions.project?.id!!}sprints"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
            }
        }
    }
}
