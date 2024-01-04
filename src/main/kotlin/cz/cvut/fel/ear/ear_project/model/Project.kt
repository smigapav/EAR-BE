package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.EmptyNameException
import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity
@Table(name = "projects")
data class Project(
    @Basic(optional = false)
    @Column(unique = true)
    var name: String? = null,
    @ManyToMany
    var users: MutableList<User> = mutableListOf(),
    @OneToMany(mappedBy = "project", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var permissions: MutableList<Permissions> = mutableListOf(),
    @OneToMany(mappedBy = "project", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var stories: MutableList<Story> = mutableListOf(),
    @OneToMany(mappedBy = "project", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var sprints: MutableList<AbstractSprint> = mutableListOf(),
) : AbstractEntity() {
    fun addUser(user: User) {
        if (users.contains(user)) {
            throw ItemAlreadyPresentException("User already present in project")
        }
        users.add(user)
    }

    fun addPermission(permission: Permissions) {
        if (permissions.contains(permission)) {
            throw ItemAlreadyPresentException("Permission already present in project")
        }
        permissions.add(permission)
    }

    fun addStory(story: Story) {
        if (stories.contains(story)) {
            throw ItemAlreadyPresentException("Story already present in project")
        }
        stories.add(story)
    }


    fun addSprint(sprint: AbstractSprint) {
        if (sprints.contains(sprint)) {
            throw ItemAlreadyPresentException("Sprint already present in project")
        }
        sprints.add(sprint)
    }

    fun removeUser(user: User) {
        if (!users.contains(user)) {
            throw ItemNotFoundException("User not found in project")
        }
        users.remove(user)
    }

    fun removeStory(story: Story) {
        if (!stories.contains(story)) {
            throw ItemNotFoundException("Story not found in project")
        }
        stories.remove(story)
    }

    fun removeSprint(sprint: AbstractSprint) {
        if (!sprints.contains(sprint)) {
            throw ItemNotFoundException("Sprint not found in project")
        }
        sprints.remove(sprint)
    }

    fun removePermission(permission: Permissions) {
        if (!permissions.contains(permission)) {
            throw ItemNotFoundException("Permission not found in project")
        }
        permissions.remove(permission)
    }
}
