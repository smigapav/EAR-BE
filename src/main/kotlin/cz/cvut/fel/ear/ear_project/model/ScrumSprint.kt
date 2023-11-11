package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.WrongStateChangeException
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("SCRUM")
class ScrumSprint(
    var start: LocalDateTime? = null,
    var finish: LocalDateTime? = null,
): AbstractSprint() {
    fun start() {
        if (state != SprintState.WAITING) {
            throw WrongStateChangeException("Sprint is already running or finished")
        }
        start = LocalDateTime.now()
        state = SprintState.RUNNING
        }

    fun finish() {
        if (state != SprintState.RUNNING) {
            throw WrongStateChangeException("Sprint is already finished or waiting")
        }
        finish = LocalDateTime.now()
        state = SprintState.FINISHED
    }
}