package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User : AbstractEntity() {
    @Basic(optional = false)
    var username: String? = null
    @Basic(optional = false)
    val webApiKey: String = generateUniqueWebApiKey()
    @OneToMany(mappedBy = "user")
    var tasks: MutableList<Task> = mutableListOf()
    @ManyToMany(mappedBy = "users")
    var projects: MutableList<Project> = mutableListOf()
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var permissions: MutableList<Permissions> = mutableListOf()
    fun addTask(task: Task) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        if (tasks!!.contains(task)) {
            throw ItemAlreadyPresentException("Task already present in user")
        }
        tasks!!.add(task)
    }

    fun addProject(project: Project) {
        if (projects == null) {
            projects = mutableListOf()
        }
        if (projects!!.contains(project)) {
            throw ItemAlreadyPresentException("Project already present in user")
        }
        projects!!.add(project)
    }

    fun addPermission(permission: Permissions) {
        if (permissions == null) {
            permissions = mutableListOf()
        }
        if (permissions!!.contains(permission)) {
            throw ItemAlreadyPresentException("Permission already present in user")
        }
        permissions!!.add(permission)
    }

    fun removeTask(task: Task) {
        if (tasks == null) {
            return
        }
        if (!tasks!!.contains(task)) {
            throw ItemNotFoundException("Task not found in user")
        }
        tasks!!.remove(task)
    }

    fun removeProject(project: Project) {
        if (projects == null) {
            return
        }
        if (!projects!!.contains(project)) {
            throw ItemNotFoundException("Project not found in user")
        }
        projects!!.remove(project)
    }

    fun removePermission(permission: Permissions) {
        if (permissions == null) {
            return
        }
        if (!permissions!!.contains(permission)) {
            throw ItemNotFoundException("Permission not found in user")
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
