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
class ScrumSprintServiceTest(
    @Autowired
    @Qualifier("scrumSprintService")
    private var abstractSprintService: AbstractSprintService,
    @Autowired
    private val scrumSprintService: ScrumSprintService,
    @Autowired
    private val em: TestEntityManager,
) {
    private lateinit var sprint: ScrumSprint
    private lateinit var story: Story
    private lateinit var project: Project

    @BeforeEach
    fun init() {
        // ScrumSprint constructor
        sprint = ScrumSprint()
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
        project.sprints.add(sprint)
        sprint.project = project
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
    fun getSprintState_StateIsWaiting() {
        val result = abstractSprintService.getSprintStatus(sprint)
        assertEquals("WAITING", result)
    }

    @Test
    fun changeSprintName_SprintsNameIsNewName() {
        abstractSprintService.changeSprintName(sprint, "New Name")
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

        abstractSprintService.addStoryToSprint(sprint, story)
        val result = em.find(ScrumSprint::class.java, sprint.id!!)
        assertTrue(result.stories.contains(story))
    }

    @Test
    fun removeStoryFromSprint_noStoryInSprint() {
        setUpStory()
        sprint.addStory(story)

        abstractSprintService.removeStoryFromSprint(sprint, story)
        val result = em.find(ScrumSprint::class.java, sprint.id!!)
        assertTrue(!result.stories.contains(story))
    }

    @Test
    fun startSprint_sprintIsStarted() {
        scrumSprintService.startSprint(sprint)
        val result = em.find(ScrumSprint::class.java, sprint.id!!)
        assertEquals("RUNNING", result.state.toString())
    }

    @Test
    fun finishSprint_sprintIsFinished() {
        sprint.start()
        scrumSprintService.finishSprint(sprint)
        val result = em.find(ScrumSprint::class.java, sprint.id!!)
        assertEquals("FINISHED", result.state.toString())
    }

    @Test
    fun getSprintStart_sprintHasStart() {
        scrumSprintService.startSprint(sprint)
        val result = scrumSprintService.getSprintStart(sprint)
        assertNotNull(result)
    }

    @Test
    fun getSprintFinish_sprintHasFinish() {
        sprint.start()
        sprint.finish()
        val result = scrumSprintService.getSprintFinish(sprint)
        assertNotNull(result)
    }

    @Test
    fun getSprintDuration_sprintHasDuration() {
        sprint.start()
        sprint.finish()
        val result = scrumSprintService.getSprintDuration(sprint)
        assertNotNull(result)
    }
}
