package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Backlog
import cz.cvut.fel.ear.ear_project.model.Story
import org.springframework.data.jpa.repository.JpaRepository

interface StoryRepository : JpaRepository<Story, Long> {
    fun findById(id: String): Story?

    fun findByName(name: String): Story?
    fun findByBacklog(backlog: Backlog): List<Story>
    //TODO: add other methods
    //fun findByState(state: String): List<Story>
    //fun findByEstimationGreaterThan(estimation: Int): List<Story>
}
