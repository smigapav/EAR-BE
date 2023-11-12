package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.Permissions
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.User
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class PermissionsServiceTest(
    @Autowired
    private val permissionsService: PermissionsService,
    @Autowired
    private val em: TestEntityManager
) {
    fun setUp(user: User, project: Project, permissions: Permissions) {
        user.username = "test"
        project.name = "test"
        em.persist(user)
        em.persist(project)
        em.persist(permissions)
        user.addProject(project)
        user.addPermission(permissions)
        project.addUser(user)
        project.addPermission(permissions)
        permissions.user = user
        permissions.project = project
        em.persist(user)
        em.persist(project)
        em.persist(permissions)
    }

    @Test
    fun changeProjectAdminPermissionTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(user, project, permissions)

        permissionsService.changeProjectAdminUserPermissions(user, project, true)

        val foundPermissions = em.find(Permissions::class.java, permissions.id)

        assertEquals(true, foundPermissions.projectAdmin)
    }

    @Test
    fun changeStoriesAndTasksManagerPermissionTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(user, project, permissions)

        permissionsService.changeStoriesAndTasksManagerUserPermissions(user, project, true)

        val foundPermissions = em.find(Permissions::class.java, permissions.id)

        assertEquals(true, foundPermissions.storiesAndTasksManager)
    }

    @Test
    fun changeSprintManagerPermissionTest() {
        val user = User()
        val project = Project()
        val permissions = Permissions()
        setUp(user, project, permissions)

        permissionsService.changeSprintManagerAdminUserPermissions(user, project, true)

        val foundPermissions = em.find(Permissions::class.java, permissions.id)

        assertEquals(true, foundPermissions.canManageSprints)
    }

    @Test
    fun changeProjectAdminPermissionThrowsExceptionWhenUserDoesNotHavePermissions() {
        val otherUser = User().apply { username = "other" }
        val project = Project()
        val permissions = Permissions()
        setUp(otherUser, project, permissions)
        em.persist(otherUser)
        assertThrows<IllegalArgumentException> {
            permissionsService.changeProjectAdminUserPermissions(otherUser, project, true)
        }
    }
}