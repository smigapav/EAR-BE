package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.*
import cz.cvut.fel.ear.ear_project.model.RunningSprint
import cz.cvut.fel.ear.ear_project.model.Sprint
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RunningSprintService(
    @Autowired
    private val sprintRepository: SprintRepository
) {
    @Transactional
    fun insertSprint(sprint: RunningSprint) {
        sprintRepository.save(sprint)
    }

    fun findAll(): List<Sprint> {
        return sprintRepository.findAll()
    }
}
