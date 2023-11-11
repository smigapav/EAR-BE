package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import cz.cvut.fel.ear.ear_project.model.ScrumSprint
import cz.cvut.fel.ear.ear_project.model.Story
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
        require(scrumSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
        sprintRepository.save(sprint)
    }

    @Transactional
    fun removeSprint(sprint: ScrumSprint) {
        require(!scrumSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
        sprintRepository.delete(sprint)
    }
    fun getSprintStatus(sprint: ScrumSprint): String {
        return sprint.state.toString()
    }

    @Transactional
    fun startSprint(sprint: ScrumSprint) {
        sprint.start()
        sprintRepository.save(sprint)
    }

    @Transactional
    fun finishSprint(sprint: ScrumSprint) {
        sprint.finish()
        sprintRepository.save(sprint)
    }
    
    fun getSprintStart(sprint: ScrumSprint): String {
        return sprint.start.toString()
    }
    
    fun getSprintFinish(sprint: ScrumSprint): String {
        return sprint.finish.toString()
    }
    
    fun getSprintDuration(sprint: ScrumSprint): String {
        return if (sprint.start == null || sprint.finish == null) {
            "Sprint is not finished yet"
        } else {
            (sprint.finish!!.toLocalDate().toEpochDay() - sprint.start!!.toLocalDate().toEpochDay()).toString()
        }
    }

    @Transactional
    fun changeSprintName(sprint: ScrumSprint, name: String) {
        sprint.name = name
        sprintRepository.save(sprint)
    }

    @Transactional
    fun addStoryToSprint(sprint: ScrumSprint, story: Story) {
        sprint.addStory(story)
        sprintRepository.save(sprint)
        story.sprint = sprint
        storyRepository.save(story)
    }

    @Transactional
    fun removeStoryFromSprint(sprint: ScrumSprint, story: Story) {
        sprint.removeStory(story)
    }

    fun scrumSprintExists(sprint: ScrumSprint): Boolean {
        return !sprintRepository.findById(sprint.id!!).isEmpty
    }
}