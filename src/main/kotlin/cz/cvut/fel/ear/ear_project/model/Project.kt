package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
@Table(name = "projects")
data class Project(
    @Basic(optional = false)
    var name: String? = null,
    @ManyToMany
    var users: MutableList<User>? = null,
    @OneToMany(mappedBy = "project")
    var permissions: MutableList<Permissions>? = null,
    @OneToMany(mappedBy = "project")
    var stories: MutableList<Story>? = null,
    @OneToOne
    var backlog: Backlog? = null,
    @OneToMany(mappedBy = "project")
    var sprints: MutableList<Sprint>? = null,
) : AbstractEntity() {
    fun addUser(user: User) {
        if (users == null) {
            users = mutableListOf()
        }
        users!!.add(user)
    }

    fun addPermission(permission: Permissions) {
        if (permissions == null) {
            permissions = mutableListOf()
        }
        permissions!!.add(permission)
    }

    fun addStory(story: Story) {
        if (stories == null) {
            stories = mutableListOf()
        }
        stories!!.add(story)
    }

    fun addSprint(sprint: Sprint) {
        if (sprints == null) {
            sprints = mutableListOf()
        }
        sprints!!.add(sprint)
    }

    fun removeUser(user: User) {
        if (users == null) {
            return
        }
        users!!.remove(user)
    }

    fun removePermission(permission: Permissions) {
        if (permissions == null) {
            return
        }
        permissions!!.remove(permission)
    }

    fun removeStory(story: Story) {
        if (stories == null) {
            return
        }
        stories!!.remove(story)
    }

    fun removeSprint(sprint: Sprint) {
        if (sprints == null) {
            return
        }
        sprints!!.remove(sprint)
    }
}
