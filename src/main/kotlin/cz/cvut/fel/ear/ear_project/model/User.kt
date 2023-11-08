package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Basic(optional = false)
    var username: String = "",
    @Basic(optional = false)
    var webApiKey: String = generateUniqueWebApiKey(),
    @OneToMany(mappedBy = "user")
    var tasks: MutableList<Task> = mutableListOf(),
    @ManyToMany(mappedBy = "users")
    var projects: MutableList<Project> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var permissions: MutableList<Permissions> = mutableListOf(),
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

    override fun toString(): String {
        return "User(id='$id', username='$username', webApiKey='$webApiKey', tasks=$tasks, projects=$projects, permissions=$permissions)"
    }

    companion object {
        fun generateUniqueWebApiKey(): String {
            // Generate a random UUID and convert it to a string
            val uuid = UUID.randomUUID().toString()

            // Create a random string to add uniqueness
            val randomString =
                (1..6)
                    .map { ('a'..'z').random() } // Use lowercase letters for randomness
                    .joinToString("")

            // Combine the UUID and random string to create the unique API key
            return "$uuid-$randomString"
        }
    }
}
