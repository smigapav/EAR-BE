package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
@DiscriminatorValue("UNSTARTED")
class UnstartedSprint : Sprint()
