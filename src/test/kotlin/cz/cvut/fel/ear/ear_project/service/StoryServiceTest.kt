package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = arrayOf(EarProjectApplication::class))
class StoryServiceTest(
    @Autowired
    private val storyService: StoryService,
    @Autowired
    private val em: TestEntityManager,
) {
    @Test
    fun changeStoryPointsTest() {
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 10
        em.persist(story)

        storyService.changeStoryPoints(story.name.toString(), 20)

        val foundStory = em.find(Story::class.java, story.id)

        assertEquals(20, foundStory.storyPoints)
    }

    @Test
    fun changeNameTest() {
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 10
        em.persist(story)

        storyService.changeName(story.name.toString(), "test2")

        val foundStory = em.find(Story::class.java, story.id)

        assertEquals("test2", foundStory.name)
    }

    @Test
    fun changeDescriptionTest() {
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 10
        em.persist(story)

        storyService.changeDescription(story.name.toString(), "test2")

        val foundStory = em.find(Story::class.java, story.id)

        assertEquals("test2", foundStory.description)
    }

    @Test
    fun createTaskTest() {
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 10
        em.persist(story)

        val task = storyService.createTask("test", "test", story.name.toString())

        val foundStory = em.find(Story::class.java, story.id)

        assertTrue(foundStory.tasks.contains(task))
        assertEquals(story, task.story)
    }

    @Test
    fun removeTaskTest() {
        val story = Story()
        story.name = "test"
        story.description = "test"
        story.storyPoints = 10
        em.persist(story)
        val task = Task()
        task.story = story
        task.name = "test"
        task.description = "test"
        em.persist(task)
        story.addTask(task)
        em.persist(story)

        storyService.removeTask(task, story.name.toString())

        val foundStory = em.find(Story::class.java, story.id)
        val foundTask = em.find(Task::class.java, task.id)

        assertTrue(!foundStory.tasks.contains(task))
        assertEquals(null, foundTask)
    }
}
