package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.*
import cz.cvut.fel.ear.ear_project.security.SecurityUtils
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.util.ReflectionTestUtils

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class ProjectServiceTest(
    @Autowired
    private val projectService: ProjectService,
    @Autowired
    private val em: TestEntityManager,
) {
    fun setUp(
        project: Project,
        user: User,
        permissions: Permissions,
    ) {
        project.name = "test"
        user.username = "test"
        user.password = "test"
        em.persist(project)
        em.persist(user)
        em.persist(permissions)
        project.addUser(user)
        project.addPermission(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        permissions.project = project
        permissions.user = user
        em.persist(project)
        em.persist(user)
        em.persist(permissions)
    }

    @Test
    fun createProjectTest() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(projectService, "securityUtils", securityUtils)

        val project = projectService.createProject("test")

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals(project, foundProject)
        assertEquals(foundProject.users[0], user)
        assertEquals(foundProject.permissions[0], user.permissions[0])
        assertEquals(user.projects[0], foundProject)
    }

    @Test
    fun changeProjectNameTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)

        projectService.changeProjectName("newName", project.name.toString())

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals("newName", foundProject.name)
    }

    @Test
    fun addExistingUserTest() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)
        val project = Project()
        project.name = "test"
        em.persist(project)

        projectService.addExistingUser(user.username.toString(), project.name.toString())

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals(user, foundProject.users[0])
    }

    @Test
    fun removeUserTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)

        projectService.removeUser(user.username.toString(), project.name.toString())

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals(0, foundProject.users.size)
        assertTrue(!user.projects.contains(project))
    }

    @Test
    fun createStoryTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)

        val story =
            projectService.createStory(
                "test",
                "test",
                1,
                project.name.toString(),
            )

        val foundStory = em.find(Story::class.java, story.id)

        assertEquals(story, foundStory)
        assertEquals(foundStory.project, project)
    }

    @Test
    fun removeStoryTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 1
        em.persist(story)
        project.addStory(story)
        em.persist(project)

        projectService.removeStory(story.name.toString(), project.name.toString())

        val foundProject = em.find(Project::class.java, project.id)
        val foundStory = em.find(Story::class.java, story.id)

        assertEquals(0, foundProject.stories.size)
        assertEquals(null, foundStory)
    }

    @Test
    fun createScrumSprintTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)

        val sprint =
            projectService.createSprint(
                "test",
                true,
                project.name.toString(),
            )

        val foundSprint = em.find(AbstractSprint::class.java, sprint.id)

        assertEquals(sprint, foundSprint)
        assertEquals(foundSprint.project, project)
        assertTrue(foundSprint is ScrumSprint)
    }

    @Test
    fun createKanbanSprintTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)

        val sprint =
            projectService.createSprint(
                "test",
                false,
                project.name.toString(),
            )

        val foundSprint = em.find(AbstractSprint::class.java, sprint.id)

        assertEquals(sprint, foundSprint)
        assertEquals(foundSprint.project, project)
        assertTrue(foundSprint is KanbanSprint)
    }

    @Test
    fun removeSprintTets() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(project, user, permissions)
        val sprint = ScrumSprint()
        sprint.name = "test"
        sprint.project = project
        em.persist(sprint)
        project.addSprint(sprint)
        em.persist(project)

        projectService.removeSprint(sprint.name!!, project.name!!)

        val foundProject = em.find(Project::class.java, project.id)
        val foundSprint = em.find(AbstractSprint::class.java, sprint.id)

        assertEquals(0, foundProject.sprints.size)
        assertEquals(null, foundSprint)
    }
}
