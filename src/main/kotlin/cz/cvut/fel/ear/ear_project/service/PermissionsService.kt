package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.PermissionsRepository
import cz.cvut.fel.ear.ear_project.dao.ProjectRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PermissionsService(
    @Autowired
    private val permissionsRepository: PermissionsRepository,
) {
    fun changeProjectAdminUserPermissions(user: User, project: Project, perm: Boolean) {
        val permissions = project.permissions.find { it.user == user }
        permissions!!.projectAdmin = perm
        permissionsRepository.save(permissions)
    }

    fun changeStoriesAndTasksManagerUserPermissions(user: User, project: Project, perm: Boolean) {
        val permissions = project.permissions.find { it.user == user }
        permissions!!.storiesAndTasksManager = perm
        permissionsRepository.save(permissions)
    }

    fun changeSprintManagerAdminUserPermissions(user: User, project: Project, perm: Boolean) {
        val permissions = project.permissions.find { it.user == user }
        permissions!!.canManageSprints = perm
        permissionsRepository.save(permissions)
    }
}