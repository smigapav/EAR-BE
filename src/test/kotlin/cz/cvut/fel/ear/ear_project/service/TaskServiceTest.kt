package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.TaskState
import jakarta.transaction.Transactional
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
class TaskServiceTest(
    @Autowired
    private val taskService: TaskService,
    @Autowired
    private val em: TestEntityManager,
) {
    @Test
    fun changeStateTest() {
        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        taskService.changeState(task.name.toString(), TaskState.IN_PROGRESS)

        val foundTask = em.find(Task::class.java, task.id)

        assertEquals(TaskState.IN_PROGRESS, foundTask.state)
    }

    @Test
    fun setTimeSpentTest() {
        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        taskService.setTimeSpent(task.name.toString(), 10)

        val foundTask = em.find(Task::class.java, task.id)

        assertEquals(10, foundTask.timeSpent)
    }

    @Test
    fun changeNameTest() {
        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        taskService.changeName(task.name.toString(), "test2")

        val foundTask = em.find(Task::class.java, task.id)

        assertEquals("test2", foundTask.name)
    }

    @Test
    fun changeDescriptionTest() {
        val task = Task()
        task.name = "test"
        task.description = "test"
        em.persist(task)

        taskService.changeDescription(task.description.toString(), "test2")

        val foundTask = em.find(Task::class.java, task.id)

        assertEquals("test2", foundTask.description)
    }

    @Test
    fun unsavedTaskOperationShouldFail() {
        val task = Task()
        task.name = "test"
        task.description = "test"

        assertThrows<NoSuchElementException> {
            taskService.changeDescription(task.name.toString(), "test2")
        }
    }
}
