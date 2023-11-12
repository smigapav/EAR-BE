import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.service.AbstractSprintService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest(classes = [EarProjectApplication::class])
class KanbanSprintServiceTest(
    @Autowired
    @Qualifier("kanbanAbstractSprintService")
    private var abstractSprintService: AbstractSprintService,
    @Autowired
    private var storyService: StoryService,
) {
    private lateinit var sprint: KanbanSprint

    @BeforeEach
    fun init() {
        sprint = abstractSprintService.createSprint("Kanban", "Sprint 1") as KanbanSprint
    }

    @Test
    fun getSprintState_StateIsWaiting() {
        val result = abstractSprintService.getSprintStatus(sprint)
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_SprintsNameIsNewName() {
        abstractSprintService.changeSprintName(sprint, "New Name")
        assertEquals("New Name", sprint.name)
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertEquals("New Name", result.name)
    }

    @Test
    fun addStoryToSprint_storyInSprint() {
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)
        abstractSprintService.addStoryToSprint(sprint, story)
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertTrue(result.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)
        abstractSprintService.addStoryToSprint(sprint, story)
        abstractSprintService.removeStoryFromSprint(sprint, story)
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertTrue(!result.stories.contains(story))
    }

    @AfterEach
    fun clear() {
        if (abstractSprintService.sprintExists(sprint)) abstractSprintService.removeSprint(sprint)
    }
}
