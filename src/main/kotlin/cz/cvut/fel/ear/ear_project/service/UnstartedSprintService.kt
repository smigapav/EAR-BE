package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.*
import cz.cvut.fel.ear.ear_project.model.*
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UnstartedSprintService(
    @Autowired
    private val sprintRepository: SprintRepository,
    @Autowired
    private val entityManager: EntityManager
) {
    @Transactional
    fun insertSprint(sprint: UnstartedSprint) {
        sprintRepository.save(sprint)
    }

    fun findAll(): List<Sprint> {
        return sprintRepository.findAll()
    }

    @Transactional
    fun changeToRunning(unstartedSprint: UnstartedSprint) {
        sprintRepository.changeTypeToRunning(unstartedSprint.id!!)
    }
}
