import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.ScrumSprint
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.service.AbstractSprintService
import cz.cvut.fel.ear.ear_project.service.ScrumSprintService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest(classes = [EarProjectApplication::class])
class ScrumSprintServiceTest(
    @Autowired
    @Qualifier("scrumAbstractSprintService")
    private var abstractSprintService: AbstractSprintService,
    @Autowired
    private var storyService: StoryService,
    @Autowired
    private var scrumSprintService: ScrumSprintService,
) {
    private lateinit var sprint: ScrumSprint

    @BeforeEach
    fun init() {
        sprint = abstractSprintService.createSprint("Scrum", "Sprint 1") as ScrumSprint
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

    @Test
    fun startSprint_SprintIsStarted() {
        scrumSprintService.startSprint(sprint)
        val result = abstractSprintService.findSprintById(sprint.id!!) as ScrumSprint
        assertEquals("RUNNING", result.state.toString())
    }

    @Test
    fun finishSprint_SprintIsFinished() {
        scrumSprintService.startSprint(sprint)
        scrumSprintService.finishSprint(sprint)
        val result = abstractSprintService.findSprintById(sprint.id!!) as ScrumSprint
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
        if (abstractSprintService.sprintExists(sprint)) abstractSprintService.removeSprint(sprint)
    }
}
