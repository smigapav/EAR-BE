package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByName(name: String): Task?

    fun findByState(state: String): List<Task>?
    fun findByStory(story: Story): List<Task>
    fun findByAssignedTo(user: User): List<Task>
}
