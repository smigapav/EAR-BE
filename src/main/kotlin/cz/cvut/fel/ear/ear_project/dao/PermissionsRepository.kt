package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Permissions
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionsRepository : JpaRepository<Permissions, Long> {
    fun findByName(name: String): Permissions?
    fun findByUser(user: User): List<Permissions>
    fun findByRole(role: String): List<Permissions>
    fun findByResource(resource: String): List<Permissions>
}
