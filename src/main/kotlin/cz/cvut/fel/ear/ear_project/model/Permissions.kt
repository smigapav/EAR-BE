package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Permissions : AbstractEntity() {
    var projectAdmin : Boolean = false
    var storiesAndTasksManager : Boolean = false
    var canManageSprints : Boolean = false

    @ManyToOne
    lateinit var user: User

    @ManyToOne
    lateinit var project: Project
}
