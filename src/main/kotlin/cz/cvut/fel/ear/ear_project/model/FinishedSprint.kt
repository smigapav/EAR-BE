 package cz.cvut.fel.ear.ear_project.model

 import jakarta.persistence.*
 import java.time.LocalDateTime

 @Entity
 @DiscriminatorValue("FINISHED")
 class FinishedSprint(
    var start: LocalDateTime? = null,
    var finish: LocalDateTime? = null,
 ) : Sprint() {
    fun setStart() {
        start = LocalDateTime.now()
    }

    fun setFinish() {
        finish = LocalDateTime.now()
    }
 }
