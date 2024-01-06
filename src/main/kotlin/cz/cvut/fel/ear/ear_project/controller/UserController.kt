package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.security.SecurityUtils
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
@RequestMapping("/user")
class UserController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val securityUtils: SecurityUtils,
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestParam("name", required = true) name: String,
        @RequestParam("password", required = true) password: String,
    ): ResponseEntity<String> {
        userService.createUser(name, password)
        return ResponseEntity("User $name created", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/deleteUser")
    fun deleteUser(): ResponseEntity<String> {
        userService.removeUser()
        return ResponseEntity("Current user deleted", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeUsername")
    fun changeUsername(
        @RequestParam("name", required = true) name: String,
    ): ResponseEntity<String> {
        userService.changeUsername(name)
        return ResponseEntity("Username changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changePassword")
    fun changePassword(
        @RequestParam("password", required = true) password: String,
    ): ResponseEntity<String> {
        userService.changePassword(password)
        return ResponseEntity("Password changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/addTask")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun addTask(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("projectName", required = true) projectName: String,
        ): ResponseEntity<String> {
        userService.addTask(taskName)
        return ResponseEntity("Task $taskName added to current user", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/removeTask")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun removeTask(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("projectName", required = true) projectName: String,
        ): ResponseEntity<String> {
        userService.removeTask(taskName)
        return ResponseEntity("Task $taskName removed from current user", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/getCurrentUser")
    fun getThisUser(): ResponseEntity<User> {
        val user = userService.getCurrentUser()
        return ResponseEntity(user, HttpStatusCode.valueOf(200))
    }
}
