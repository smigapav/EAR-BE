package cz.cvut.fel.ear.ear_project.controller

import cz.cvut.fel.ear.ear_project.service.StoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/story")
class StoryController(
    @Autowired
    private val storyService: StoryService,
) {
    @PostMapping("/changeStoryPoints")
    @PreAuthorize("hasRoleByStoryName(storyName, 'manager')")
    fun changeStoryPoints(
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("storyPoints", required = true) storyPoints: Int,
    ): ResponseEntity<String> {
        storyService.changeStoryPoints(storyName, storyPoints)
        return ResponseEntity("Story points changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeStoryName")
    @PreAuthorize("hasRoleByStoryName(storyName, 'manager')")
    fun changeName(
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("newName", required = true) newName: String,
    ): ResponseEntity<String> {
        storyService.changeName(storyName, newName)
        return ResponseEntity("Story name changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/changeStoryDescription")
    @PreAuthorize("hasRoleByStoryName(storyName, 'manager')")
    fun changeDescription(
        @RequestParam("storyName", required = true) storyName: String,
        @RequestParam("description", required = true) description: String,
    ): ResponseEntity<String> {
        storyService.changeDescription(storyName, description)
        return ResponseEntity("Story description changed", HttpStatusCode.valueOf(200))
    }

    @PostMapping("/createTask")
    @PreAuthorize("hasRoleByStoryName(storyName, 'manager')")
    fun createTask(
        @RequestParam("name", required = true) name: String,
        @RequestParam("description", required = true) description: String,
        @RequestParam("storyName", required = true) storyName: String,
    ): ResponseEntity<String> {
        storyService.createTask(name, description, storyName)
        return ResponseEntity("Task created", HttpStatusCode.valueOf(200))
    }
}
