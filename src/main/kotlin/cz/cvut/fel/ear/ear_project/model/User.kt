package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "users")
class User(
    @Basic(optional = false)
    @Column(unique = true)
    var username: String? = null,
    @Basic(optional = false)
    var password: String? = null,
    @OneToMany(mappedBy = "user")
    var tasks: MutableList<Task> = mutableListOf(),
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    var projects: MutableList<Project> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER)
    var permissions: MutableList<Permissions> = mutableListOf(),
) : AbstractEntity() {
    fun addTask(task: Task) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        if (tasks.contains(task)) {
            throw ItemAlreadyPresentException("Task already present in user")
        }
        tasks.add(task)
    }

    fun addProject(project: Project) {
        if (projects == null) {
            projects = mutableListOf()
        }
        if (projects.contains(project)) {
            throw ItemAlreadyPresentException("Project already present in user")
        }
        projects.add(project)
    }

    fun addPermission(permission: Permissions) {
        if (permissions == null) {
            permissions = mutableListOf()
        }
        if (permissions.contains(permission)) {
            throw ItemAlreadyPresentException("Permission already present in user")
        }
        permissions.add(permission)
    }

    fun removeTask(task: Task) {
        if (tasks == null) {
            return
        }
        if (!tasks.contains(task)) {
            throw ItemNotFoundException("Task not found in user")
        }
        tasks.remove(task)
    }

    fun removeProject(project: Project) {
        if (projects == null) {
            return
        }
        if (!projects.contains(project)) {
            throw ItemNotFoundException("Project not found in user")
        }
        projects.remove(project)
    }

    fun removePermission(permission: Permissions) {
        if (permissions == null) {
            return
        }
        if (!permissions.contains(permission)) {
            throw ItemNotFoundException("Permission not found in user")
        }
        permissions.remove(permission)
    }

    fun encodePassword(encoder: PasswordEncoder) {
        password = encoder.encode(password)
    }

    override fun toString(): String {
        return "User(id='$id', username='$username', password='$password', tasks=$tasks, projects=$projects, permissions=$permissions)"
    }
}
