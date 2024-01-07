package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoryService(
    @Autowired
    private val storyRepository: StoryRepository,
    @Autowired
    private val taskRepository: TaskRepository,
) {
    fun changeStoryPoints(
        storyName: String,
        storyPoints: Int,
    ): Story {
        val story = findStoryByName(storyName)
        story.storyPoints = storyPoints
        storyRepository.save(story)
        return story
    }

    fun changeName(
        storyName: String,
        newName: String,
    ): Story {
        val story = findStoryByName(storyName)
        story.name = newName
        storyRepository.save(story)
        return story
    }

    fun changeDescription(
        storyName: String,
        description: String,
    ): Story {
        val story = findStoryByName(storyName)
        story.description = description
        storyRepository.save(story)
        return story
    }

    @Transactional
    fun createTask(
        name: String,
        description: String,
        storyName: String,
    ): Task {
        val story = findStoryByName(storyName)
        val task = Task()
        task.name = name
        task.description = description
        task.story = story
        taskRepository.save(task)
        story.addTask(task)
        storyRepository.save(story)
        return task
    }

    @Transactional
    fun removeTask(
        taskName: String,
        storyName: String,
    ) {
        val story = findStoryByName(storyName)
        val task = findTaskByName(taskName)
        story.removeTask(task)
        storyRepository.save(story)
        taskRepository.delete(task)
    }

    fun findStoryByName(name: String): Story {
        return storyRepository.findByName(name) ?: throw NoSuchElementException("Story with name $name not found")
    }

    fun findTaskByName(name: String): Task {
        return taskRepository.findByName(name) ?: throw NoSuchElementException("Task with name $name not found")
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }

    fun storyExists(story: Story): Boolean {
        return story.id != null && !storyRepository.findById(story.id!!).isEmpty
    }

    fun toString(story: Story): String {
        return (story.id.toString() + " " + story.name + " " + story.description + " " + story.storyPoints)
    }
}
