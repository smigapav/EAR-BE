package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.model.AbstractSprint
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sprint")
class SprintController(
    @Autowired
    private val abstractSprintService: AbstractSprintService,
    @Autowired
    private val kanbanSprintService: KanbanSprintService,
    @Autowired
    private val scrumSprintService: ScrumSprintService,
) {
    @PostMapping("/changeName")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun changeName(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("newName", required = true) newName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        abstractSprintService.changeSprintName(sprintName, newName)
        return ResponseEntity("Sprint name changed to $newName", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/addStoryToSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun addStoryToSprint(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        abstractSprintService.addStoryToSprint(sprintName, storyName)
        return ResponseEntity("Story $storyName added to sprint $sprintName", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/removeStoryFromSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun removeStoryFromSprint(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        abstractSprintService.removeStoryFromSprint(sprintName, storyName)
        return ResponseEntity("Story $storyName removed from sprint $sprintName", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/getSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun getSprint(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<AbstractSprint> {
        val sprint = abstractSprintService.findSprintByName(sprintName)
        return ResponseEntity(sprint, HttpStatusCode.valueOf(200))
    }

    @PostMapping("/startSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun startSprint(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        scrumSprintService.startSprint(sprintName)
        return ResponseEntity("Sprint $sprintName started", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/finishSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun finishSprint(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        scrumSprintService.finishSprint(sprintName)
        return ResponseEntity("Sprint $sprintName finished", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/getSprintDuration")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun getSprintDuration(
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        val duration = scrumSprintService.getSprintDuration(sprintName)
        return ResponseEntity("Sprint duration is $duration", HttpStatusCode.valueOf(200))
    }
}
