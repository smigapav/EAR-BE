package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.*
import cz.cvut.fel.ear.ear_project.model.*
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
    private val backlogRepository: BacklogRepository,
    @Autowired
    private val permissionsRepository: PermissionsRepository,
    @Autowired
    private val storyRepository: StoryRepository,
    @Autowired
    private val sprintRepository: SprintRepository,
) {
    @Transactional
    fun createProject(user: User, project: Project) {
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        val permissions = Permissions(
            true,
            true,
            true,
            user,
            project
        )
        val backlog = Backlog(
            project
        )
        project.addUser(user)
        project.addPermission(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        project.backlog = backlog
        permissionsRepository.save(permissions)
        projectRepository.save(project)
        backlogRepository.save(backlog)
        userRepository.save(user)
    }

    fun changeProjectName(name: String, project: Project) {
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        project.name = name
        projectRepository.save(project)
    }

    @Transactional
    fun addExistingUser(user: User, project: Project) {
        if (!userExists(user) || !projectExists(project)) {
            throw IllegalArgumentException("User or Project does not exist")
        }
        val permissions = Permissions()
        permissions.user = user
        permissions.project = project
        project.addUser(user)
        project.addPermission(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        permissionsRepository.save(permissions)
        projectRepository.save(project)
        userRepository.save(user)
    }

    @Transactional
    fun removeUser(user: User, project: Project) {
        if (!userExists(user) || !projectExists(project)) {
            throw IllegalArgumentException("User or Project does not exist")
        }
        val permissions = project.permissions.find { it.user == user }
        project.removeUser(user)
        project.removePermission(permissions!!)
        user.removeProject(project)
        user.removePermission(permissions)
        permissionsRepository.delete(permissions)
        projectRepository.save(project)
        userRepository.save(user)
    }

    @Transactional
    fun createStory(story: Story, project: Project) {
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        val backlog = project.backlog
        backlog!!.addStory(story)
        project.addStory(story)
        story.project = project
        story.backlog = backlog
        projectRepository.save(project)
        storyRepository.save(story)
        backlogRepository.save(backlog)
    }

    @Transactional
    fun removeStory(story: Story, project: Project) {
        if (!storyExists(story) || !projectExists(project)) {
            throw IllegalArgumentException("Story or Project does not exist")
        }
        val backlog = project.backlog
        project.removeStory(story)
        backlog!!.removeStory(story)
        projectRepository.save(project)
        storyRepository.delete(story)
    }

    @Transactional
    fun createSprint(sprint: AbstractSprint, project: Project) {
        if (!projectExists(project)) {
            throw IllegalArgumentException("Project does not exist")
        }
        project.addSprint(sprint)
        sprint.project = project
        projectRepository.save(project)
        sprintRepository.save(sprint)
    }

    @Transactional
    fun removeSprint(sprint: AbstractSprint, project: Project) {
        if (!sprintExists(sprint) || !projectExists(project)) {
            throw IllegalArgumentException("Sprint or project does not exist")
        }
        project.removeSprint(sprint)
        projectRepository.save(project)
        sprintRepository.delete(sprint)
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