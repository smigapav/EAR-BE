package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.EarProjectApplication
import cz.cvut.fel.ear.ear_project.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    @Qualifier("kanbanSprintService")
    private var abstractSprintService: SprintService,
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
        user.password = "test"
        em.persist(user)

        // Project constructor
        project = Project()
        project.name = "test"
        em.persist(project)
        project.sprints.add(sprint)
        sprint.project = project
    }

    fun setUpStory() {
        // Story constructor
        story = Story()
        story.name = "Story 1"
        story.description = "Description 1"
        story.storyPoints = 1
        em.persist(story)
    }

    @Test
    fun getSprintState_stateIsWaiting() {
        val result = abstractSprintService.getSprintStatus(sprint)
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_sprintsNameIsNewName() {
        abstractSprintService.changeSprintName("Sprint 1", "New Name")
        assertEquals("New Name", sprint.name)
    }

    @Test
    fun findSprintById_fountSprint() {
        val result = abstractSprintService.findSprintById(sprint.id!!) as AbstractSprint
        assertEquals("Sprint 1", result.name)
    }

    @Test
    fun addStoryToSprint_storyInSprint() {
        setUpStory()

        abstractSprintService.addStoryToSprint("Sprint 1", "Story 1")
        val result = em.find(KanbanSprint::class.java, sprint.id!!)
        assertTrue(result.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        setUpStory()
        sprint.addStory(story)

        assertNotNull(abstractSprintService.removeStoryFromSprint("Sprint 1", "Story 1"))
        val result = em.find(KanbanSprint::class.java, sprint.id!!)
        assertTrue(!result.stories.contains(story))
    }
}
