package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Backlog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BacklogRepository : JpaRepository<Backlog, Long> {
    // fun findByProject(project: String): List<Backlog>
    // fun findByStory(story: Story): List<Backlog>
}
