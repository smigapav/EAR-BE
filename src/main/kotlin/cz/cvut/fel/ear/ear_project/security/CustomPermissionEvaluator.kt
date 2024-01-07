import cz.cvut.fel.ear.ear_project.service.ProjectService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator(
    private val projectService: ProjectService,
) : PermissionEvaluator {
    val accessDeniedException = "Access is denied"
    override fun hasPermission(
        authentication: Authentication,
        targetDomainObject: Any?,
        permission: Any?,
    ): Boolean {
        // Implement your custom permission logic here
        if (targetDomainObject is String && permission is String) {
            val hasPermission = hasRoleByProjectName(authentication, targetDomainObject, permission)
            if (!hasPermission) {
                throw AccessDeniedException(accessDeniedException)
            }
            return true
        }
        return false
    }

    override fun hasPermission(
        authentication: Authentication,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?,
    ): Boolean {
        // Implement your custom permission logic here
        if (targetType is String && permission is String) {
            val hasPermission = hasRoleByProjectName(authentication, targetType, permission)
            if (!hasPermission) {
                throw AccessDeniedException(accessDeniedException)
            }
        }
        return false
    }

    private fun hasRoleByProjectName(
        authentication: Authentication,
        projectName: String,
        role: String,
    ): Boolean {
        val projectId = projectService.findProjectByName(projectName).id.toString()
        val requiredAuthority = SimpleGrantedAuthority("$projectId$role")
        if (!authentication.authorities.contains(requiredAuthority)) {
            throw AccessDeniedException(accessDeniedException)
        }
        return true
    }
}
