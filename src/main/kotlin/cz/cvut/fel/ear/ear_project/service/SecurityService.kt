package cz.cvut.fel.ear.ear_project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityService(
    @Autowired
    private val projectService: ProjectService,
    @Autowired
    private val storyService: StoryService,
) {
    fun hasRoleByProjectName(
        projectName: String,
        role: String,
    ): Boolean {
        val projectId = projectService.findProjectByName(projectName).id.toString()
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "$projectId$role" }
    }

    fun hasRoleByStoryName(
        storyName: String,
        role: String,
    ): Boolean {
        val story = storyService.findStoryByName(storyName)
        val projectId = story.project?.id.toString()
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "$projectId$role" }
    }
}
