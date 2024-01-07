package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.PermissionsRepository
import cz.cvut.fel.ear.ear_project.dao.ProjectRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.Permissions
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PermissionsService(
    @Autowired
    private val permissionsRepository: PermissionsRepository,
    @Autowired
    private val projectRepository: ProjectRepository,
    @Autowired
    private val userRepository: UserRepository,
) {
    fun changePermission(
        username: String,
        projectName: String,
        perm: Boolean,
        permissionSetter: Permissions.(Boolean) -> Unit,
    ): Permissions? {
        val user = findUserByName(username)
        val project = findProjectByName(projectName)
        val permissions =
            project.permissions.find { it.user == user }
                ?: throw IllegalArgumentException("User isn't part of this project")
        permissions.apply {
            permissionSetter(perm)
            permissionsRepository.save(this)
        }
        return permissions
    }

    fun changeProjectAdminUserPermissions(
        username: String,
        projectName: String,
        perm: Boolean,
    ) = changePermission(username, projectName, perm, Permissions::projectAdmin::set)

    fun changeStoriesAndTasksManagerUserPermissions(
        username: String,
        projectName: String,
        perm: Boolean,
    ) = changePermission(username, projectName, perm, Permissions::storiesAndTasksManager::set)

    fun changeSprintManagerAdminUserPermissions(
        username: String,
        projectName: String,
        perm: Boolean,
    ) = changePermission(username, projectName, perm, Permissions::canManageSprints::set)

    fun findAllPermissions(): List<Permissions> = permissionsRepository.findAll()

    fun findPermissionById(id: Long): Permissions = permissionsRepository.findById(id).orElse(null)

    fun findProjectByName(name: String): Project =
        projectRepository.findByName(name) ?: throw NoSuchElementException("Project with name $name not found")

    fun findUserByName(name: String): User =
        userRepository.findByUsername(name) ?: throw NoSuchElementException("User with name $name not found")

    fun permissionsExists(permissions: Permissions): Boolean = permissions.id?.let { permissionsRepository.findById(it).isPresent } ?: false
}
