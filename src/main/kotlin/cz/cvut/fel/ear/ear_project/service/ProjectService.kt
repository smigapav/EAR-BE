package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.*
import cz.cvut.fel.ear.ear_project.model.*
import cz.cvut.fel.ear.ear_project.security.PermissionsUtils.Companion.updateUserPermissions
import cz.cvut.fel.ear.ear_project.security.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProjectService(
    @Autowired
    private val projectRepository: ProjectRepository,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val permissionsRepository: PermissionsRepository,
    @Autowired
    private val storyRepository: StoryRepository,
    @Autowired
    private val sprintRepository: SprintRepository,
    @Autowired
    private val securityUtils: SecurityUtils,
) {
    @Transactional
    fun createProject(name: String): Project {
        val user = securityUtils.currentUser!!
        if (!userExists(user)) {
            throw IllegalArgumentException("User isn't logged in")
        }
        val project = Project()
        project.name = name
        projectRepository.save(project)
        val permissions =
            Permissions(
                true,
                true,
                true,
                user,
                project,
            )
        permissionsRepository.save(permissions)
        project.addUser(user)
        project.addPermission(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        permissionsRepository.save(permissions)
        userRepository.save(user)
        projectRepository.save(project)

        updateUserPermissions(user)

        return project
    }

    fun changeProjectName(
        name: String,
        projectName: String,
    ) {
        val project = findProjectByName(projectName)
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        project.name = name
        projectRepository.save(project)
    }

    @Transactional
    fun addExistingUser(
        username: String,
        projectName: String,
    ) {
        val project = findProjectByName(projectName)
        val user = findUserByName(username)
        if (!userExists(user) || !projectExists(project)) {
            throw IllegalArgumentException("User or Project does not exist")
        }
        val permissions = Permissions()
        permissions.user = user
        permissions.project = project
        permissionsRepository.save(permissions)
        project.addUser(user)
        project.addPermission(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        projectRepository.save(project)
        userRepository.save(user)
    }

    @Transactional
    fun removeUser(
        username: String,
        projectName: String,
    ) {
        val user = findUserByName(username)
        val project = findProjectByName(projectName)
        if (!userExists(user) || !projectExists(project)) {
            throw IllegalArgumentException("User or Project does not exist")
        }
        val permissions = project.permissions.find { it.user == user }
        project.removeUser(user)
        project.removePermission(permissions!!)
        user.removeProject(project)
        user.removePermission(permissions)
        projectRepository.save(project)
        userRepository.save(user)
        permissionsRepository.delete(permissions)
    }

    @Transactional
    fun createStory(
        name: String,
        description: String,
        storyPoints: Int,
        projectName: String,
    ): Story {
        val project = findProjectByName(projectName)
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        val story = Story()
        story.name = name
        story.description = description
        story.storyPoints = storyPoints
        storyRepository.save(story)
        project.addStory(story)
        story.project = project
        projectRepository.save(project)
        storyRepository.save(story)
        return story
    }

    @Transactional
    fun removeStory(
        storyName: String,
        projectName: String,
    ) {
        val story = findStoryByName(storyName)
        val project = findProjectByName(projectName)
        if (!storyExists(story) || !projectExists(project)) {
            throw IllegalArgumentException("Story or Project does not exist")
        }
        project.removeStory(story)
        projectRepository.save(project)
        storyRepository.delete(story)
    }

    @Transactional
    fun createSprint(
        name: String,
        createScrumSprint: Boolean,
        projectName: String,
    ): AbstractSprint {
        val project = findProjectByName(projectName)
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        val sprint: AbstractSprint = if (createScrumSprint) {
            ScrumSprint()
        } else {
            KanbanSprint()
        }
        sprint.name = name
        project.addSprint(sprint)
        sprint.project = project
        projectRepository.save(project)
        sprintRepository.save(sprint)
        return sprint
    }

    @Transactional
    fun removeSprint(
        sprintName: String,
        projectName: String,
    ) {
        val sprint = findSprintByName(sprintName)
        val project = findProjectByName(projectName)
        if (!sprintExists(sprint) || !projectExists(project)) {
            throw IllegalArgumentException("Sprint or project does not exist")
        }
        project.removeSprint(sprint)
        projectRepository.save(project)
        sprintRepository.delete(sprint)
    }

    fun findProjectByName(name: String): Project {
        return projectRepository.findByName(name) ?: throw NoSuchElementException("Project with name $name not found")
    }

    fun findUserByName(name: String): User {
        return userRepository.findByUsername(name) ?: throw NoSuchElementException("User with name $name not found")
    }

    fun findStoryByName(name: String): Story {
        return storyRepository.findByName(name) ?: throw NoSuchElementException("Story with name $name not found")
    }

    fun findSprintByName(name: String): AbstractSprint {
        return sprintRepository.findByName(name) ?: throw NoSuchElementException("Sprint with name $name not found")
    }

    fun storyExists(story: Story): Boolean {
        return !storyRepository.findById(story.id!!).isEmpty
    }

    fun projectExists(project: Project): Boolean {
        return !projectRepository.findById(project.id!!).isEmpty
    }

    fun userExists(user: User): Boolean {
        return !userRepository.findById(user.id!!).isEmpty
    }

    fun sprintExists(sprint: AbstractSprint): Boolean {
        return !sprintRepository.findById(sprint.id!!).isEmpty
    }
}
