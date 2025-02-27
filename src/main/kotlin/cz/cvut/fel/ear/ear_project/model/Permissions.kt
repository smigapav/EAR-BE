package cz.cvut.fel.ear.ear_project.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "permissions")
data class Permissions(
    var projectAdmin: Boolean = false,
    var storiesAndTasksManager: Boolean = false,
    var canManageSprints: Boolean = false,
    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.REMOVE])
    var user: User? = null,
    @JsonBackReference
    @ManyToOne
    var project: Project? = null,
) : AbstractEntity() {
    fun addUser(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    fun removeUser() {
        this.user = null
    }

    fun addProject(project: Project) {
        if (this.project != project) {
            this.project = project
        }
    }

    fun removeProject() {
        this.project = null
    }

    override fun toString(): String {
        return "Permissions(project=${project?.name}, projectAdmin=$projectAdmin, storiesAndTasksManager=$storiesAndTasksManager, canManageSprints=$canManageSprints)"
    }
}
