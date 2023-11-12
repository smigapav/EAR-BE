package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import cz.cvut.fel.ear.ear_project.model.Story
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KanbanSprintService (
    @Autowired
    private val sprintRepository: SprintRepository,
    @Autowired
    private val storyRepository: StoryRepository,
){
    @Transactional
    fun createSprint(sprint: KanbanSprint) {
        require(!kanbanSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
        sprintRepository.saveAndFlush(sprint)
    }

    @Transactional
    fun removeSprint(sprint: KanbanSprint) {
        validateSprintExists(sprint)
        sprintRepository.delete(sprint)
    }
    fun getSprintState(sprint: KanbanSprint): String? {
        if (sprint.id == null) {
            return null
        }
        return (sprintRepository.findById(sprint.id!!).orElse(null) ?: return null).state.toString()
    }

    @Transactional
    fun changeSprintName(sprint: KanbanSprint, name: String) {
        validateSprintExists(sprint)
        sprint.name = name
        sprintRepository.save(sprint)
    }

    @Transactional
    fun addStoryToSprint(sprint: KanbanSprint, story: Story) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.addStory(story)
        sprintRepository.save(sprint)
        story.sprint = sprint
        storyRepository.save(story)
    }

    @Transactional
    fun removeStoryFromSprint(sprint: KanbanSprint, story: Story) {
        validateSprintExists(sprint)
        validateStoryExists(story)
        sprint.removeStory(story)
        sprintRepository.save(sprint)
    }

    fun kanbanSprintExists(sprint: KanbanSprint): Boolean {
        if (sprint.id == null) {
            return false
        }
        return sprintRepository.findById(sprint.id!!).isPresent
    }

    fun findKanbanSprintById(id: Long): AbstractSprint? {
        return sprintRepository.findById(id).orElse(null)
    }

    private fun validateSprintExists(sprint: KanbanSprint) {
        require(kanbanSprintExists(sprint)) { throw IllegalArgumentException("Sprint does not exist") }
    }

    private fun validateStoryExists(story: Story) {
        require(if (story.id == null) { false } else { storyRepository.findById(story.id!!).isPresent}) { throw IllegalArgumentException("Story does not exist") }
    }
}