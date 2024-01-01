import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.core.Authentication

class CustomMethodSecurityExpressionRoot(
    private val authentication: Authentication,
    private val projectService: ProjectService,
    private val storyService: StoryService
) : SecurityExpressionRoot(authentication), MethodSecurityExpressionOperations {

    private var filterObject: Any? = null
    private var returnObject: Any? = null

    fun hasRoleByProjectName(root: MethodSecurityExpressionOperations, projectName: String, role: String): Boolean {
        val projectId = projectService.findProjectByName(projectName).id.toString()
        return root.hasRole("$projectId$role")
    }

    fun hasRoleByStoryName(root: MethodSecurityExpressionOperations, storyName: String, role: String): Boolean {
        val story = storyService.findStoryByName(storyName)
        val projectId = story.project?.id.toString()
        return root.hasRole("$projectId$role")
    }

    override fun setFilterObject(filterObject: Any?) {
        this.filterObject = filterObject
    }

    override fun getFilterObject(): Any? {
        return filterObject
    }

    override fun setReturnObject(returnObject: Any?) {
        this.returnObject = returnObject
    }

    override fun getReturnObject(): Any? {
        return returnObject
    }

    override fun getThis(): Any {
        return this
    }
}
