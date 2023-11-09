package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "Permissions")
data class Permissions(
    var projectAdmin : Boolean = false,

    var storiesAndTasksManager : Boolean = false,

    var canManageSprints : Boolean = false,

    @ManyToOne
    var user: User? = null,

    @ManyToOne
    var project: Project? = null
) : AbstractEntity()
