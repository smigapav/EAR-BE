package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class KanbanSprintService(
    @Autowired
    sprintRepository: SprintRepository,
    @Autowired
    storyRepository: StoryRepository,
) : SprintService(sprintRepository, storyRepository)
