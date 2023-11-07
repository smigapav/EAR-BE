package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Task : AbstractEntity() {

    var state: State = State.WAITING

    var timeSpent: Int = 0

    @Basic(optional = false)
    lateinit var name: String

    @Basic(optional = false)
    lateinit var description: String

    @ManyToOne
    lateinit var story : Story

    @ManyToOne(optional = true)
    var user : User? = null
}
