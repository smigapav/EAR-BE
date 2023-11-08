import cz.cvut.fel.ear.ear_project.model.Backlog
import org.springframework.data.jpa.repository.JpaRepository

interface BacklogRepository : JpaRepository<Backlog, Long> {
    fun findByName(name: String): Backlog?
    fun findByProject(project: String): List<Backlog>
    fun findByStatus(status: String): List<Backlog>
}
