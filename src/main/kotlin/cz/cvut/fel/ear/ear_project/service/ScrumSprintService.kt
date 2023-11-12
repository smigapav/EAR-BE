package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.ScrumSprint
import cz.cvut.fel.ear.ear_project.model.SprintState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScrumSprintService(
    @Autowired
    sprintRepository: SprintRepository,
    @Autowired
    storyRepository: StoryRepository,
) : AbstractSprintService(sprintRepository, storyRepository) {

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
}
