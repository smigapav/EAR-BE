import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.service.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    fun insertUserAddsUserIntoDB() {
        val user = User()
        user.username = "user1"
        userService.insertUser(user)

        val result = em.find(User::class.java, user.id!!)

        assertEquals(user, result)
    }

    @Test
    fun removeUserDeletesUserFromDB() {
        val user = User()
        user.username = "user1"
        em.persist(user)

        userService.removeUser(user)

        val result = em.find(User::class.java, user.id!!)

        assertEquals(null, result)
    }
}
