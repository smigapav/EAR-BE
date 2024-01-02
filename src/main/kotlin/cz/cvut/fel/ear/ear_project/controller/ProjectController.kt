package cz.cvut.fel.ear.ear_project.controller

import CustomPermissionEvaluator
import cz.cvut.fel.ear.ear_project.service.ProjectService
import cz.cvut.fel.ear.ear_project.service.SecurityService
import cz.cvut.fel.ear.ear_project.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/project")
class ProjectController(
    @Autowired
    private val projectService: ProjectService,
    @Autowired
    private val securityService: SecurityService,
    @Autowired
    private val userService: UserService,
) {
    @PostMapping("/createProject")
    fun registerUser(
        @RequestParam("name", required = true) name: String,
    ): ResponseEntity<String> {
        projectService.createProject(name)
        println(userService.getAllPermissions())
        return ResponseEntity("Project $name created", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeProjectName")
    fun changeProjectName(
        @RequestParam("newName", required = true) newName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        projectService.changeProjectName(newName, projectName)
        return ResponseEntity("Project name changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRoleByProjectName(#projectName, 'admin')")
    fun addUser(
        @RequestParam("username", required = true) username: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        if (!securityService.hasRoleByProjectName(projectName, "admin")) {
            return ResponseEntity("User is not an admin", HttpStatusCode.valueOf(401))
        }
        projectService.addExistingUser(username, projectName)
        return ResponseEntity("User added", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/removeUser")
    @PreAuthorize("hasRoleByProjectName(#projectName, 'admin')")
    fun removeUser(
        @RequestParam("username", required = true) username: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        projectService.removeUser(username, projectName)
        return ResponseEntity("User removed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/createStory")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun createStory(
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("description", required = true) description: String,
        @RequestParam("storyPoints", required = true) storyPoints: Int,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        try {
            projectService.createStory(storyName, description, storyPoints, projectName)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity(e.message, HttpStatusCode.valueOf(400))
        }
        return ResponseEntity("Story created", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/removeStory")
    @PreAuthorize("hasRoleByProjectName(#projectName, 'manager')")
    fun removeStory(
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        projectService.removeStory(storyName, projectName)
        return ResponseEntity("Story removed", HttpStatusCode.valueOf(200))
    }
}
