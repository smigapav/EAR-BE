package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SPRINT_TYPE")
@Entity
abstract class Sprint : AbstractEntity() {
    @Basic(optional = false)
    lateinit var name: String
}
