 package cz.cvut.fel.ear.ear_project.model

 import jakarta.persistence.*
 import java.time.LocalDateTime

 @Entity
 @DiscriminatorValue("RUNNING")
 class RunningSprint(
    var start: LocalDateTime? = null,
 ) : Sprint() {
    fun setStart() {
        start = LocalDateTime.now()
    }
 }
