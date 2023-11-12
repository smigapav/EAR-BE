package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.Story
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
abstract class AbstractSprintService(
    @Autowired
    protected val sprintRepository: SprintRepository,
    @Autowired
    protected val storyRepository: StoryRepository,
) {
    @Transactional
    abstract fun createSprint(
        sprintType: String,
        name: String,
    ): AbstractSprint


    @Transactional
    fun removeSprint(sprint: AbstractSprint) {
        validateSprintExists(sprint)
        sprintRepository.delete(sprint)
    }

    fun getSprintStatus(sprint: AbstractSprint): String? {
        if (sprint.id == null) {
            return null
        }
        return (sprintRepository.findById(sprint.id!!).orElse(null) ?: return null).state.toString()
    }

    @Transactional
    fun changeSprintName(
        sprint: AbstractSprint,
        name: String,
    ) {
        validateSprintExists(sprint)
        sprint.name = name
        sprintRepository.save(sprint)
    }

    @Transactional
    fun addStoryToSprint(
        sprint: AbstractSprint,
        story: Story,
    ) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.addStory(story)
        sprintRepository.save(sprint)
        story.sprint = sprint
        storyRepository.save(story)
    }

    @Transactional
    fun removeStoryFromSprint(
        sprint: AbstractSprint,
        story: Story,
    ) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.removeStory(story)
        sprintRepository.save(sprint)
    }

    fun findSprintById(id: Long): AbstractSprint? {
        return sprintRepository.findById(id).orElse(null)
    }

    fun sprintExists(sprint: AbstractSprint): Boolean {
        if (sprint.id == null) {
            return false
        }
        return sprintRepository.findById(sprint.id!!).isPresent
    }

    protected fun validateSprintExists(sprint: AbstractSprint) {
        require(sprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
    }

    protected fun validateStoryExists(story: Story) {
        require(
            if (story.id == null) {
                false
            } else {
                storyRepository.findById(story.id!!).isPresent
            },
        ) { throw IllegalArgumentException("Story does not exist") }
    }
}
