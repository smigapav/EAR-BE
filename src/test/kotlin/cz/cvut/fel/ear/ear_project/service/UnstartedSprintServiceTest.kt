package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.model.RunningSprint
import cz.cvut.fel.ear.ear_project.model.UnstartedSprint
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class UnstartedSprintServiceTest(
    @Autowired
    private val unstartedSprintService: UnstartedSprintService,
    @Autowired
    private val runningSprintService: RunningSprintService,
    @Autowired
    private val em: TestEntityManager,
    @Autowired
    private val sprintRepository: SprintRepository,
) {
    @Test
    fun startUnstartedSprint() {
        val unstartedSprint = UnstartedSprint()
        unstartedSprint.name = "unstartedSprint1"
        sprintRepository.save(unstartedSprint)
        unstartedSprintService.changeToRunning(unstartedSprint)
        val sprint = sprintRepository.findById(unstartedSprint.id!!).get()
        when (sprint is UnstartedSprint) {
            true -> {
                runningSprintService.startSprint(sprint)
            }
            else -> {
                throw IllegalArgumentException("Unknown sprint type: ${sprint is UnstartedSprint}")
            }
        }
        /*val runningSprint = sprintRepository.findById(unstartedSprint.id!!)
        runningSprintService.startSprint(runningSprint)*/
    }

}