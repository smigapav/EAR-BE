package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository : JpaRepository<Story, Long> {
    fun findByName(name: String): Story?
}
