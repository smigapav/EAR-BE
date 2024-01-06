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
    fun removeSprint(sprintName: String) {
        val sprint = findSprintByName(sprintName)
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
        sprintName: String,
        name: String,
    ) {
        val sprint = findSprintByName(sprintName)
        validateSprintExists(sprint)
        sprint.name = name
        sprintRepository.save(sprint)
    }

    @Transactional
    fun addStoryToSprint(
        sprintName: String,
        storyName: String,
    ) {
        val sprint = findSprintByName(sprintName)
        val story = findStoryByName(storyName)
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.addStory(story)
        sprintRepository.save(sprint)
        story.sprint = sprint
        storyRepository.save(story)
    }

    @Transactional
    fun removeStoryFromSprint(
        sprintName: String,
        storyName: String,
    ) {
        val sprint = findSprintByName(sprintName)
        val story = findStoryByName(storyName)
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

    fun findSprintByName(name: String): AbstractSprint {
        return sprintRepository.findByName(name) ?: throw NoSuchElementException("Sprint with name $name not found")
    }

    private fun findStoryByName(name: String): Story {
        return storyRepository.findByName(name) ?: throw NoSuchElementException("Story with name $name not found")
    }
}
