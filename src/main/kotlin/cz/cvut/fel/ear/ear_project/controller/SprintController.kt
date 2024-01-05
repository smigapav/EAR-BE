package cz.cvut.fel.ear.ear_project.controller

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
    @Autowired
    private val sprintFactory: SprintFactory,
) {
    @PostMapping("/createSprint")
    @PreAuthorize("hasPermission(#projectName, 'sprints')")
    fun createSprint(
        @RequestParam("sprintType", required = true) sprintType: String,
        @RequestParam("sprintName", required = true) sprintName: String,
        @RequestParam("projectName", required = true) projectName: String,
    ): ResponseEntity<String> {
        sprintFactory.createSprint(sprintType, sprintName)
        return ResponseEntity("$sprintType sprint $sprintName created", HttpStatusCode.valueOf(200))
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
}
