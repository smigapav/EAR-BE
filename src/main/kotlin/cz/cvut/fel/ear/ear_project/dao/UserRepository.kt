package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    @Query(name = "User.findAllUsersProjects")
    fun findAllUsersProjects(
        @Param("userId") userId: Long,
    ): List<Project>
}
