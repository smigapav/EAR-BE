package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Sprint
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    //@Query("SELECT u FROM User u JOIN u.permissions p WHERE p.name = :permission")
    //fun findByPermission(@Param("permission") permission: String): List<User>?
    //fun findByTask(task: Task): List<User>
    //fun findByStory(story: Story): List<User>
    //fun findBySprint(sprint: Sprint): List<User>
}
