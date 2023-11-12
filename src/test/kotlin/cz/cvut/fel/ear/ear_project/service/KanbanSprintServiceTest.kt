import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import cz.cvut.fel.ear.ear_project.model.SprintState
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.service.KanbanSprintService
import cz.cvut.fel.ear.ear_project.service.StoryService
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@Transactional
@AutoConfigureTestEntityManager
@SpringBootTest(classes = [EarProjectApplication::class])
class KanbanSprintServiceTest (
    @Autowired
    private var kanbanSprintService: KanbanSprintService,
    @Autowired
    private var storyService: StoryService,
) {

    private val sprint = KanbanSprint()

    @BeforeEach
    fun init() {
        sprint.name = "Sprint 1"
        kanbanSprintService.createSprint(sprint)
    }

    @Test
    fun getSprintState_StateIsWaiting() {

        // Act
        val result = kanbanSprintService.getSprintState(sprint)

        // Assert
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_SprintsNameIsNewName() {
        // Act
        kanbanSprintService.changeSprintName(sprint, "New Name")

        // Assert
        assertEquals("New Name", sprint.name)

        // KanbanSprint name from db
        val result = kanbanSprintService.findKanbanSprintById(sprint.id!!) as KanbanSprint
        assertEquals("New Name", result.name)
    }

    @Test
    fun addStoryToSprint_storyInSprint() {
        // Arrange
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)

        // Act
        kanbanSprintService.addStoryToSprint(sprint, story)

        // Check db
        val result = kanbanSprintService.findKanbanSprintById(sprint.id!!) as KanbanSprint
        assertEquals("Sprint 1", result.name)

        // Assert
        assertTrue(sprint.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        // Arrange
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)

        kanbanSprintService.addStoryToSprint(sprint, story)

        val result = kanbanSprintService.findKanbanSprintById(sprint.id!!) as KanbanSprint
        assertEquals(mutableListOf(story), result.stories)

        // Act
        kanbanSprintService.removeStoryFromSprint(sprint, story)

        // Assert
        assertTrue(!sprint.stories.contains(story))
    }

    @AfterEach
    fun clear() {
        if (kanbanSprintService.kanbanSprintExists(sprint)) kanbanSprintService.removeSprint(sprint)
    }
}