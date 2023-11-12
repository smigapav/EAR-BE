package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import jakarta.transaction.Transactional
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
class UserServiceTest(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val em: TestEntityManager,
) {
    @Test
    fun insertUserIntoDB() {
        val user = userService.createUser("test")

        val foundUser = em.find(User::class.java, user.id)

        assertEquals(user, foundUser)
    }

    @Test
    fun removeUserFromDB() {
        val user = User()
        user.username = "test"
        em.persist(user)

        userService.removeUser(user)

        val foundUser = em.find(User::class.java, user.id)

        assertEquals(null, foundUser)
    }

    @Test
    fun addTaskToUser() {
        val user = User()
        user.username = "test"
        em.persist(user)
        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        userService.addTask(user, task)

        val foundUser = em.find(User::class.java, user.id)

        assertTrue(foundUser.tasks.contains(task))
        assertEquals(user, task.user)
    }

    @Test
    fun addTaskToUserShouldThrowError() {
        val user = User()
        user.username = "test"
        em.persist(user)
        val task = Task()
        task.name = "test"
        task.description = "test"

        assertThrows<IllegalArgumentException> {
            userService.addTask(user, task)
        }
    }

    @Test
    fun removeTaskFromUser() {
        val user = User()
        user.username = "test"
        val task = Task()
        task.name = "test"
        task.description = "test"
        user.addTask(task)
        task.user = user
        em.persist(user)
        em.persist(task)

        userService.removeTask(user, task)

        val foundUser = em.find(User::class.java, user.id)
        val userTasks = foundUser.tasks

        assertTrue(userTasks.isEmpty() && task.user == null)
    }

    @Test
    fun changeUsernameTest() {
        val user = User()
        user.username = "test"
        em.persist(user)

        userService.changeUsername(user, "test2")

        val foundUser = em.find(User::class.java, user.id)

        assertEquals("test2", foundUser.username)
    }
}
