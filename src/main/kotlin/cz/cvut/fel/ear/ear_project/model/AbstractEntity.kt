package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.io.Serializable

@MappedSuperclass
abstract class AbstractEntity : Serializable {
    @Id
    @GeneratedValue
    var id: Int? = null
}
