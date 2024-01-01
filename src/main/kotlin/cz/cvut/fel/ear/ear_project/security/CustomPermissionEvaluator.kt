import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.Authentication
import java.io.Serializable

class CustomPermissionEvaluator(
    private val projectService: ProjectService,
    private val storyService: StoryService
) : PermissionEvaluator {

    override fun hasPermission(authentication: Authentication, targetDomainObject: Any?, permission: Any?): Boolean {
        // Implement your custom permission logic here
        if (targetDomainObject is String && permission is String) {
            val root = createSecurityExpressionRoot(authentication)
            return hasRoleByProjectName(root, targetDomainObject, permission)
        }
        return false
    }

    override fun hasPermission(authentication: Authentication, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        // Implement your custom permission logic here
        return false
    }

    fun hasRoleByProjectName(root: MethodSecurityExpressionOperations, projectName: String, role: String): Boolean {
        val projectId = projectService.findProjectByName(projectName).id.toString()
        return root.hasRole("$projectId$role")
    }

    fun hasRoleByStoryName(root: MethodSecurityExpressionOperations, storyName: String, role: String): Boolean {
        val story = storyService.findStoryByName(storyName)
        val projectId = story.project?.id.toString()
        return root.hasRole("$projectId$role")
    }

    private fun createSecurityExpressionRoot(authentication: Authentication): MethodSecurityExpressionOperations {
        return CustomMethodSecurityExpressionRoot(authentication, projectService, storyService)
    }
}
