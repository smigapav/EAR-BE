package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.service.PermissionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/permission")
class PermissionsController(
    @Autowired
    private val permissionsService: PermissionsService,
) {
    @PostMapping("/changePermission")
    @PreAuthorize("hasPermission(#projectName, 'admin')")
    fun changePermission(
        @RequestParam("username", required = true) username: String,
        @RequestParam("projectName", required = true) projectName: String,
        @RequestParam("permission", required = true) permission: Boolean,
        @RequestParam("permissionType", required = true) permissionType: String,
    ): ResponseEntity<String> {
        when (permissionType) {
            "admin" -> permissionsService.changeProjectAdminUserPermissions(username, projectName, permission)
            "manager" -> permissionsService.changeStoriesAndTasksManagerUserPermissions(username, projectName, permission)
            "sprints" -> permissionsService.changeSprintManagerAdminUserPermissions(username, projectName, permission)
            else -> throw IllegalArgumentException("Invalid permission type")
        }
        return ResponseEntity("Permissions changed for user $username", HttpStatusCode.valueOf(200))
    }
}
