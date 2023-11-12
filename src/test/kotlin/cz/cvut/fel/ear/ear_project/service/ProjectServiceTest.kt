package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.*
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class ProjectServiceTest(
    @Autowired
    private val projectService: ProjectService,
    @Autowired
    private val em: TestEntityManager
) {

    fun setUp(project: Project, backlog: Backlog, user: User, permissions: Permissions) {
        project.name = "test"
        user.username = "test"
        em.persist(project)
        em.persist(backlog)
        em.persist(user)
        em.persist(permissions)
        project.backlog = backlog
        project.addUser(user)
        project.addPermission(permissions)
        backlog.project = project
        user.addProject(project)
        user.addPermission(permissions)
        permissions.project = project
        permissions.user = user
        em.persist(project)
        em.persist(backlog)
        em.persist(user)
        em.persist(permissions)
    }
    @Test
    fun createProjectTest() {
        val user = User()
        user.username = "test"
        em.persist(user)

        val project = projectService.createProject("test", user)

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
        val backlog = Backlog()
        setUp(project, backlog, user, permissions)

        projectService.changeProjectName("newName", project)

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals("newName", foundProject.name)
    }

    @Test
    fun addExistingUserTest() {
        val user = User()
        user.username = "test"
        em.persist(user)
        val project = Project()
        project.name = "test"
        em.persist(project)

        projectService.addExistingUser(user, project)

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals(user, foundProject.users[0])
    }

    @Test
    fun removeUserTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        val backlog = Backlog()
        setUp(project, backlog, user, permissions)

        projectService.removeUser(user, project)

        val foundProject = em.find(Project::class.java, project.id)

        assertEquals(0, foundProject.users.size)
        assertTrue(!user.projects.contains(project))
    }

    @Test
    fun createStoryTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        val backlog = Backlog()
        setUp(project, backlog, user, permissions)

        val story = projectService.createStory(
            "test",
            "test",
            1,
            project)

        val foundStory = em.find(Story::class.java, story.id)

        assertEquals(story, foundStory)
        assertEquals(foundStory.project, project)
        assertEquals(foundStory.backlog, backlog)
    }

    @Test
    fun removeStoryTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        val backlog = Backlog()
        setUp(project, backlog, user, permissions)
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.price = 1
        em.persist(story)
        project.addStory(story)
        backlog.addStory(story)
    }
}