import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.service.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class UserServiceTest(
    @Autowired
    private val userService: UserService,
) {
    @Transactional
    @Test
    fun insertUser() {
        val user = User(username = "test")
        userService.insertUser(user)

        val result = userService.findUserById(user.id!!)

        println(result)

        assertEquals(user, result)
    }
}
