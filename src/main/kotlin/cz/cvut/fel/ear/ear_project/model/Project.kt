package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Project : AbstractEntity() {
    @Basic(optional = false)
    lateinit var name : String

    @ManyToMany
    lateinit var users : List<User>

    @OneToMany(mappedBy = "project")
    lateinit var permissions: List<Permissions>

    @OneToMany(mappedBy = "project")
    lateinit var stories : List<Story>

    @OneToOne
    lateinit var backlog: Backlog

    @OneToMany(mappedBy = "project")
    lateinit var sprints : MutableList<Sprint>
}
