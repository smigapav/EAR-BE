package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("FINISHED")
class FinishedSprint : Sprint()
