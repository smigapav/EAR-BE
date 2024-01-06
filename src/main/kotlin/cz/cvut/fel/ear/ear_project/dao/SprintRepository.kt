package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SprintRepository : JpaRepository<AbstractSprint, Long> {
    fun findByName(name: String): AbstractSprint?
}
