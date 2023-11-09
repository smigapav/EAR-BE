package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Sprint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SprintRepository : JpaRepository<Sprint, Long> {
//    fun findById(id: String): Sprint?
//    fun findByName(name: String): Sprint?
//    fun findByProject(project: String): List<Sprint>
//    fun findByStartDateBetween(start: LocalDate, end: LocalDate): List<Sprint>
//    fun findByStatus(status: String): List<Sprint>
}
