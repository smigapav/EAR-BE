package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Sprint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SprintRepository : JpaRepository<Sprint, String> {
    fun findByName(name: String): Sprint?
    fun findByProjectName(projectName: String): List<Sprint>
    //fun findByStatus(status: String): List<Sprint>
}
