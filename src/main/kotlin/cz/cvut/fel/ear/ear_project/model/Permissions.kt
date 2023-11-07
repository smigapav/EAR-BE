package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity

@Entity
class Permissions : AbstractEntity() {
    var projectAdmin : Boolean = false
    var storiesAndTasksManager : Boolean = false
    var canManageSprints : Boolean = false
}
