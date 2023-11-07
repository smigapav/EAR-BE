package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity

@Entity
class Project : AbstractEntity() {
    @Basic(optional = false)
    lateinit var name : String
}
