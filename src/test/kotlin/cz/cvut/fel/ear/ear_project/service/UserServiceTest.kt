package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.security.SecurityUtils
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.util.ReflectionTestUtils

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class UserServiceTest(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val em: TestEntityManager,
) {
    @Test
    fun insertUserIntoDB() {
        val user = userService.createUser("test", "test")

        val foundUser = em.find(User::class.java, user.id)

        assertEquals(user, foundUser)
    }

    @Test
    fun removeUserFromDB() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        userService.removeUser()

        val foundUser = em.find(User::class.java, user.id)

        assertEquals(null, foundUser)
    }

    @Test
    fun addTaskToUser() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        userService.addTask("test")

        val foundUser = em.find(User::class.java, user.id)

        assertTrue(foundUser.tasks.contains(task))
        assertEquals(user, task.user)
    }

    @Test
    fun addTaskToUserShouldThrowError() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        val task = Task()
        task.name = "test"
        task.description = "test"

        assertThrows<NoSuchElementException> {
            userService.addTask("test")
        }
    }

    @Test
    fun removeTaskFromUser() {
        val user = User()
        user.username = "test"
        user.password = "test"
        val task = Task()
        task.name = "test"
        task.description = "test"
        user.addTask(task)
        task.user = user
        em.persist(user)
        em.persist(task)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        userService.removeTask("test")

        val foundUser = em.find(User::class.java, user.id)
        val userTasks = foundUser.tasks

        assertTrue(userTasks.isEmpty() && task.user == null)
    }

    @Test
    fun changeUsernameTest() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        userService.changeUsername("test2")

        val foundUser = em.find(User::class.java, user.id)

        assertEquals("test2", foundUser.username)
    }

    @Test
    fun changePasswordTest() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)

        userService.changePassword("test2")

        val foundUser = em.find(User::class.java, user.id)

        val passwordEncoder = ReflectionTestUtils.getField(userService, "passwordEncoder") as PasswordEncoder

        assertEquals(true, passwordEncoder.matches("test2", foundUser.password))
    }

    fun findAllUsersProjectsTest() {
        val user = User()
        user.username = "test"
        user.password = "test"
        em.persist(user)

        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityUtils = Mockito.mock(SecurityUtils::class.java)
        Mockito.`when`(securityUtils.currentUser).thenReturn(user)
        SecurityContextHolder.getContext().authentication = authentication

        // Use reflection to set the private securityUtils field in UserService
        ReflectionTestUtils.setField(userService, "securityUtils", securityUtils)


    }
}
