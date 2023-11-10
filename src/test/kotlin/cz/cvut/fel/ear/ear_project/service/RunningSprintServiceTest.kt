import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.RunningSprint
import cz.cvut.fel.ear.ear_project.model.Sprint
import cz.cvut.fel.ear.ear_project.model.UnstartedSprint
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.service.RunningSprintService
import cz.cvut.fel.ear.ear_project.service.UnstartedSprintService
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
class RunningSprintServiceTest(
    @Autowired
    private val runningSprintService: RunningSprintService,
    @Autowired
    private val unstartedSprintService: UnstartedSprintService,
    @Autowired
    private val em: TestEntityManager
) {
    @Test
    fun insertSprintAddsUserIntoDB() {
        val runningSprint = RunningSprint()
        runningSprint.name = "runningSprint1"
        runningSprintService.insertSprint(runningSprint)
        val unstartedSprint = UnstartedSprint()
        unstartedSprint.name = "unstartedSprint1"
        unstartedSprintService.insertSprint(unstartedSprint)

        val result = em.find(Sprint::class.java, runningSprint.id!!)
//        user.username = "user1"
//        userService.insertUser(user)
//
//        val result = em.find(User::class.java, user.id!!)
//
        println(runningSprintService.findAll())
        assertEquals(runningSprint, result)
    }
}
