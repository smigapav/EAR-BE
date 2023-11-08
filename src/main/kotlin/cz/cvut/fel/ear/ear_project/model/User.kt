package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
data class User(
    @Basic(optional = false)
    var username: String? = null,
    @Basic(optional = false)
    var webApiKey: String? = null,
    @OneToMany(mappedBy = "user")
    var tasks: MutableList<Task>? = null,
    @ManyToMany(mappedBy = "users")
    var projects: MutableList<Project>? = null,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var permissions: MutableList<Permissions>? = null,
) : AbstractEntity() {
    fun addTask(task: Task) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        tasks!!.add(task)
    }

    fun addProject(project: Project) {
        if (projects == null) {
            projects = mutableListOf()
        }
        projects!!.add(project)
    }

    fun addPermission(permission: Permissions) {
        if (permissions == null) {
            permissions = mutableListOf()
        }
        permissions!!.add(permission)
    }

    fun removeTask(task: Task) {
        if (tasks == null) {
            return
        }
        tasks!!.remove(task)
    }

    fun removeProject(project: Project) {
        if (projects == null) {
            return
        }
        projects!!.remove(project)
    }

    fun removePermission(permission: Permissions) {
        if (permissions == null) {
            return
        }
        permissions!!.remove(permission)
    }
}
