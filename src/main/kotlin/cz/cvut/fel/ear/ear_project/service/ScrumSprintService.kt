package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScrumSprintService(
    @Autowired
    private val sprintRepository: SprintRepository,
    @Autowired
    private val storyRepository: StoryRepository
) {

    @Transactional
    fun createSprint(sprint: ScrumSprint) {
        require(!scrumSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
        sprintRepository.saveAndFlush(sprint)
    }

    @Transactional
    fun removeSprint(sprint: ScrumSprint) {
        validateSprintExists(sprint)
        sprintRepository.delete(sprint)
    }
    fun getSprintStatus(sprint: ScrumSprint): String? {
        if (sprint.id == null) {
            return null
        }
        return (sprintRepository.findById(sprint.id!!).orElse(null) ?: return null).state.toString()
    }

    @Transactional
    fun startSprint(sprint: ScrumSprint) {
        validateSprintExists(sprint)
        require(sprint.state == SprintState.WAITING) { throw IllegalStateException("Sprint is not waiting") }
        sprint.start()
        sprintRepository.save(sprint)
    }

    @Transactional
    fun finishSprint(sprint: ScrumSprint) {
        validateSprintExists(sprint)
        require(sprint.state == SprintState.RUNNING) { throw IllegalStateException("Sprint is not active") }
        sprint.finish()
        sprintRepository.save(sprint)
    }
    
    fun getSprintStart(sprint: ScrumSprint): String {
        validateSprintExists(sprint)
        return sprint.start.toString()
    }
    
    fun getSprintFinish(sprint: ScrumSprint): String {
        validateSprintExists(sprint)
        return sprint.finish.toString()
    }
    
    fun getSprintDuration(sprint: ScrumSprint): String {
        validateSprintExists(sprint)
        return if (sprint.start == null || sprint.finish == null) {
            "Sprint is not finished yet"
        } else {
            (sprint.finish!!.toLocalDate().toEpochDay() - sprint.start!!.toLocalDate().toEpochDay()).toString()
        }
    }

    @Transactional
    fun changeSprintName(sprint: ScrumSprint, name: String) {
        validateSprintExists(sprint)
        require(name.isNotBlank()) { throw IllegalArgumentException("Name cannot be empty") }
        sprint.name = name
        sprintRepository.save(sprint)
    }

    @Transactional
    fun addStoryToSprint(sprint: ScrumSprint, story: Story) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.addStory(story)
        sprintRepository.save(sprint)
        story.sprint = sprint
        storyRepository.save(story)
    }

    @Transactional
    fun removeStoryFromSprint(sprint: ScrumSprint, story: Story) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.removeStory(story)
        sprintRepository.save(sprint)
    }

    fun findScrumSprintById(id: Long): AbstractSprint? {
        return sprintRepository.findById(id).orElse(null)
    }

    fun scrumSprintExists(sprint: ScrumSprint): Boolean {
        if (sprint.id == null) {
            return false
        }
        return sprintRepository.findById(sprint.id!!).isPresent
    }

    private fun validateSprintExists(sprint: ScrumSprint) {
        require(scrumSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
    }

    private fun validateStoryExists(story: Story) {
        require(if (story.id == null) { false } else { storyRepository.findById(story.id!!).isPresent}) { throw IllegalArgumentException("Story does not exist") }
    }
}