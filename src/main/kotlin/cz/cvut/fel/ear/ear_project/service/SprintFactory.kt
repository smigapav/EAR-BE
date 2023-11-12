package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.KanbanSprint
import cz.cvut.fel.ear.ear_project.model.ScrumSprint
import org.springframework.stereotype.Component

@Component
class SprintFactory {
    fun createSprint(
        sprintType: String,
        name: String,
    ): AbstractSprint {
        return when (sprintType) {
            "Kanban" -> KanbanSprint().apply { this.name = name }
            "Scrum" -> ScrumSprint().apply { this.name = name }
            else -> throw IllegalArgumentException("Invalid sprint type")
        }
    }
}
