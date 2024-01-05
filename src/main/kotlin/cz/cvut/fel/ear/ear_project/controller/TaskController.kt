package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.TaskState
import cz.cvut.fel.ear.ear_project.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController(
    @Autowired
    private val taskService: TaskService
) {
    @PostMapping("/changeState")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun changeState(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("taskState", required = true) taskState: TaskState,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        taskService.changeState(taskName, taskState)
        return ResponseEntity("Task state changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/setTimeSpent")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun setTimeSpent(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("timeSpent", required = true) timeSpent: Int,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        taskService.setTimeSpent(taskName, timeSpent)
        return ResponseEntity("Task time spent set", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeName")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun changeName(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("newName", required = true) newName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        taskService.changeName(taskName, newName)
        return ResponseEntity("Task name changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeDescription")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun changeDescription(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("description", required = true) description: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        taskService.changeDescription(taskName, description)
        return ResponseEntity("Task description changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/getTask")
    @PreAuthorize("hasPermission(#projectName, 'manager')")
    fun getTask(
        @RequestParam("taskName", required = true) taskName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<Task> {
        val task = taskService.findByName(taskName)
        return ResponseEntity(task, HttpStatusCode.valueOf(200))
    }
}
