package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired
    private val userService: UserService,
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestParam("name", required = true) name: String,
        @RequestParam("password", required = true) password: String,
    ): ResponseEntity<String> {
        userService.createUser(name, password)
        return ResponseEntity("User $name created", HttpStatusCode.valueOf(200))
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
}
