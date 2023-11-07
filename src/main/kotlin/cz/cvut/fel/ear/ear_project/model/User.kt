package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity

@Entity
class User : AbstractEntity() {
    @Basic(optional = false)
    lateinit var username: String

    @Basic(optional = false)
    lateinit var webApiKey: String
}
