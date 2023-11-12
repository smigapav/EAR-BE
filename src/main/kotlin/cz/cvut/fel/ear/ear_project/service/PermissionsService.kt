package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.PermissionsRepository
import cz.cvut.fel.ear.ear_project.dao.ProjectRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.Permissions
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PermissionsService(
    @Autowired
    private val permissionsRepository: PermissionsRepository,
) {
    fun changeProjectAdminUserPermissions(user: User, project: Project, perm: Boolean): Permissions? {
        val permissions = project.permissions.find { it.user == user }
        if (permissions == null) {
            throw IllegalArgumentException("User does not have permissions for this project")
        }
        permissions!!.projectAdmin = perm
        permissionsRepository.save(permissions)
        return permissions
    }

    fun changeStoriesAndTasksManagerUserPermissions(user: User, project: Project, perm: Boolean): Permissions? {
        val permissions = project.permissions.find { it.user == user }
        if (permissions == null) {
            throw IllegalArgumentException("User does not have permissions for this project")
        }
        permissions!!.storiesAndTasksManager = perm
        permissionsRepository.save(permissions)
        return permissions
    }

    fun changeSprintManagerAdminUserPermissions(user: User, project: Project, perm: Boolean): Permissions? {
        val permissions = project.permissions.find { it.user == user }
        if (permissions == null) {
            throw IllegalArgumentException("User does not have permissions for this project")
        }
        permissions!!.canManageSprints = perm
        permissionsRepository.save(permissions)
        return permissions
    }

    fun findAllPermissions(): List<Permissions> {
        return permissionsRepository.findAll()
    }

    fun findPermissionById(id: Long): Permissions {
        return permissionsRepository.findById(id).orElse(null)
    }

    fun permissionsExists(permissions: Permissions): Boolean {
        return permissions.id != null && !permissionsRepository.findById(permissions.id!!).isEmpty
    }
}