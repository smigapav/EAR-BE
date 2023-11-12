package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KanbanSprintService(
    @Autowired
    sprintRepository: SprintRepository,
    @Autowired
    storyRepository: StoryRepository,
) : AbstractSprintService(sprintRepository, storyRepository) {
}
