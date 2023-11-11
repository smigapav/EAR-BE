package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.RunningSprint
import cz.cvut.fel.ear.ear_project.model.Sprint
import cz.cvut.fel.ear.ear_project.model.UnstartedSprint
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@DataJpaTest
class SprintRepositoryTest(
    @Autowired
    val sprintRepository: SprintRepository
) {
    @Test
    fun findSprintById() {
        val runningSprint = RunningSprint()
        runningSprint.name = "runningSprint1"
        sprintRepository.save(runningSprint)
        val result = sprintRepository.findSprintTypeById(runningSprint.id!!)
        println(result)
    }

    // Tests that Sprint type is changed to RUNNING correctly
    @Test
    fun startUnstartedSprint_changedToRUNNINGInDb() {
        val unstartedSprint = UnstartedSprint()
        unstartedSprint.name = "unstartedSprint1"
        sprintRepository.save(unstartedSprint)

        // when
        sprintRepository.changeTypeToRunning(unstartedSprint.id!!)

        // then
        val result = unstartedSprint.id?.let { sprintRepository.findById(it) }
        val result1 = unstartedSprint.id?.let { sprintRepository.findSprintTypeById(it) }
        assertEquals("unstartedSprint1", result?.get()?.name)
        assertEquals("RUNNING", result1)
    }

    /*
    @Test
    fun startUnstartedSprint_changedToRUNNINGInDb() {

        val unstartedSprint = UnstartedSprint()
        unstartedSprint.name = "unstartedSprint1"
        unstartedSprintRepository.save(unstartedSprint)

        val runningSprint = RunningSprint()
        runningSprint.id = unstartedSprint.id
        runningSprint.name = unstartedSprint.name
        runningSprint.project = unstartedSprint.project
        runningSprint.stories = unstartedSprint.stories
        runningSprint.setStart()

        val result1 = unstartedSprint.id?.let { unstartedSprintRepository.findById(it) }
        println(result1)
        unstartedSprintRepository.delete(unstartedSprint)

        runningSprintRepository.save(runningSprint)

        val result = unstartedSprint.id?.let { runningSprintRepository.findById(it) }
        println(result)
        assertEquals("unstartedSprint1", result1?.get()?.name)
    }

     */
}