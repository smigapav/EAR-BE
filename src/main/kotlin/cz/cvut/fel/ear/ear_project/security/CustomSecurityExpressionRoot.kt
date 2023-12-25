package cz.cvut.fel.ear.ear_project.security

import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.core.Authentication

class CustomSecurityExpressionRoot(
    authentication: Authentication,
    private val projectService: ProjectService,
    private val storyService: StoryService,
) : SecurityExpressionRoot(authentication) {

    fun hasRoleByProjectName(projectName: String, role: String): Boolean {
        val projectId = projectService.findProjectByName(projectName).id.toString()
        return this.hasRole("$projectId$role")
    }

    fun hasRoleByStoryName(storyName: String, role: String): Boolean {
        val story = storyService.findStoryByName(storyName)
        val projectId = story.project?.id.toString()
        return this.hasRole("$projectId$role")
    }
}