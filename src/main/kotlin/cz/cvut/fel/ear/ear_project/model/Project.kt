package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.EmptyNameException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity
@Table(name = "projects")
data class Project(
    @Basic(optional = false)
    var name: String? = null,
    @ManyToMany
    var users: MutableList<User> = mutableListOf(),
    @OneToMany(mappedBy = "project")
    var permissions: MutableList<Permissions> = mutableListOf(),
    @OneToMany(mappedBy = "project")
    var stories: MutableList<Story> = mutableListOf(),
    @OneToOne
    var backlog: Backlog? = null,
    @OneToMany(mappedBy = "project")
    var sprints: MutableList<Sprint> = mutableListOf(),
) : AbstractEntity() {
    fun addUser(user: User) {
        if (users.contains(user)) {
            throw ItemNotFoundException("User already present in project")
        }
        users.add(user)
    }

    fun addPermission(permission: Permissions) {
        if (!permissions.contains(permission)) {
            permissions.add(permission)
        }
    }

    fun addStory(story: Story) {
        if (stories.contains(story)) {
            throw ItemNotFoundException("Story already present in project")
        }
        stories.add(story)
    }

    fun changeName(name: String) {
        if (name.isEmpty()) {
            throw EmptyNameException("Name cannot be empty")
        }
        this.name = name
    }

    fun addSprint(sprint: Sprint) {
        if (sprints.contains(sprint)) {
            throw ItemNotFoundException("Sprint already present in project")
        }
        sprints.add(sprint)
    }

    fun removeUser(user: User) {
        if (!users.contains(user)) {
            throw ItemNotFoundException("User not found in project")
        } else if (users.size == 1) {
            throw ItemNotFoundException("Cannot remove last user from project")
        }
        users.remove(user)
    }

    fun removeStory(story: Story) {
        if (!stories.contains(story)) {
            throw ItemNotFoundException("Story not found in project")
        }
        stories.remove(story)
    }

    fun removeSprint(sprint: Sprint) {
        if (!sprints.contains(sprint)) {
            throw ItemNotFoundException("Sprint not found in project")
        }
        sprints.remove(sprint)
    }

    fun removePermission(permission: Permissions) {
        permissions.remove(permission)
    }
}
