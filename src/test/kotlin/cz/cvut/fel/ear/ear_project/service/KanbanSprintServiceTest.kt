import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.*
import cz.cvut.fel.ear.ear_project.service.AbstractSprintService
import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.StoryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest(classes = [EarProjectApplication::class])
@AutoConfigureTestEntityManager
class KanbanSprintServiceTest(
    @Autowired
    private var abstractSprintService: AbstractSprintService,
    @Autowired
    private var projectService: ProjectService,
    @Autowired
    private val em: TestEntityManager,
) {
    private lateinit var sprint: KanbanSprint
    private lateinit var story: Story
    private lateinit var project: Project

    @BeforeEach
    fun init() {
        // KanbanSprint constructor
        sprint = KanbanSprint()
        sprint.name = "Sprint 1"
        em.persist(sprint)

        // User constructor
        val user = User()
        user.username = "test"
        em.persist(user)

        // Project constructor
        project = Project()
        project.name = "test"
        em.persist(project)
        projectService.createProject(user, project)
        projectService.createSprint(sprint, project)
    }

    fun setUpStory() {
        // Story constructor
        story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.price = 1
        em.persist(story)
    }

    @Test
    fun getSprintState_stateIsWaiting() {
        val result = abstractSprintService.getSprintStatus(sprint)
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_sprintsNameIsNewName() {
        abstractSprintService.changeSprintName(sprint, "New Name")
        assertEquals("New Name", sprint.name)
    }

    @Test
    fun findSprintById_fountSprint() {
        abstractSprintService.changeSprintName(sprint, "New Name")
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertEquals("New Name", result.name)
    }

    @Test
    fun addStoryToSprint_storyInSprint() {
        setUpStory()
        projectService.createStory(story, project)

        abstractSprintService.addStoryToSprint(sprint, story)
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertTrue(result.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        setUpStory()
        abstractSprintService.addStoryToSprint(sprint, story)

        assertNotNull(abstractSprintService.removeStoryFromSprint(sprint, story))
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertTrue(!result.stories.contains(story))
    }
}
