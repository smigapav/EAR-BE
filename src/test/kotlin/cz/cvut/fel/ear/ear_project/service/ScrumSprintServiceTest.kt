import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.ScrumSprint
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.service.ScrumSprintService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest(classes = [EarProjectApplication::class])
class ScrumSprintServiceTest (
    @Autowired
    private var scrumSprintService: ScrumSprintService,
    @Autowired
    private var storyService: StoryService,
) {

    private val sprint = ScrumSprint()

    @BeforeEach
    fun init() {
        sprint.name = "Sprint 1"
        scrumSprintService.createSprint(sprint)
    }

    @Test
    fun getSprintStatus_StatusIsWaiting() {
        val result = scrumSprintService.getSprintStatus(sprint)
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_SprintsNameIsNewName() {
        scrumSprintService.changeSprintName(sprint, "New Name")
        assertEquals("New Name", sprint.name)
        val result = scrumSprintService.findScrumSprintById(sprint.id!!) as ScrumSprint
        assertEquals("New Name", result.name)
    }

    @Test
    fun addStoryToSprint_storyInSprint() {
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)
        scrumSprintService.addStoryToSprint(sprint, story)
        val result = scrumSprintService.findScrumSprintById(sprint.id!!) as ScrumSprint
        assertTrue(result.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        val story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        storyService.createStory(story)
        scrumSprintService.addStoryToSprint(sprint, story)
        scrumSprintService.removeStoryFromSprint(sprint, story)
        val result = scrumSprintService.findScrumSprintById(sprint.id!!) as ScrumSprint
        assertTrue(!result.stories.contains(story))
    }

    @Test
    fun startSprint_SprintIsStarted() {
        scrumSprintService.startSprint(sprint)
        val result = scrumSprintService.findScrumSprintById(sprint.id!!) as ScrumSprint
        assertEquals("RUNNING", result.state.toString())
    }

    @Test
    fun finishSprint_SprintIsFinished() {
        scrumSprintService.startSprint(sprint)
        scrumSprintService.finishSprint(sprint)
        val result = scrumSprintService.findScrumSprintById(sprint.id!!) as ScrumSprint
        assertEquals("FINISHED", result.state.toString())
    }

    @Test
    fun getSprintStart_SprintHasStart() {
        scrumSprintService.startSprint(sprint)
        val result = scrumSprintService.getSprintStart(sprint)
        assertNotNull(result)
    }

    @Test
    fun getSprintFinish_SprintHasFinish() {
        scrumSprintService.startSprint(sprint)
        scrumSprintService.finishSprint(sprint)
        val result = scrumSprintService.getSprintFinish(sprint)
        assertNotNull(result)
    }

    @Test
    fun getSprintDuration_SprintHasDuration() {
        scrumSprintService.startSprint(sprint)
        scrumSprintService.finishSprint(sprint)
        val result = scrumSprintService.getSprintDuration(sprint)
        assertNotNull(result)
    }

    @AfterEach
    fun clear() {
        if (scrumSprintService.scrumSprintExists(sprint)) scrumSprintService.removeSprint(sprint)
    }
}