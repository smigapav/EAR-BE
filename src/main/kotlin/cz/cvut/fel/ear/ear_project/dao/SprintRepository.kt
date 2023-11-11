package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SprintRepository : JpaRepository<AbstractSprint, Long> {
}
