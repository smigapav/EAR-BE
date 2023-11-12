package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("KANBAN")
class KanbanSprint : AbstractSprint() {
    override var state: SprintState = SprintState.WAITING
        get() {
            if (field == SprintState.WAITING) {
                return SprintState.WAITING
            }
            for (story in stories) {
                for (task in story.tasks) {
                    if (task.taskState != TaskState.DONE || task.taskState != TaskState.REJECTED) {
                        return SprintState.RUNNING
                    }
                }
            }
            return SprintState.FINISHED
        }
}
