import cz.cvut.fel.ear.ear_project.model.Backlog
import cz.cvut.fel.ear.ear_project.model.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BacklogRepository : JpaRepository<Backlog, Long> {
    //fun findByProject(project: String): List<Backlog>
    //fun findByStory(story: Story): List<Backlog>
}
