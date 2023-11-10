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
    fun changePrice(story: Story, price: Int) {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.price = price
        storyRepository.save(story)
    }

    fun changeName(story: Story, name: String) {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.name = name
        storyRepository.save(story)
    }

    fun changeDescription(story: Story, description: String) {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.description = description
        storyRepository.save(story)
    }

    @Transactional
    fun createTask(task: Task, story: Story) {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        task.story = story
        story.addTask(task)
        storyRepository.save(story)
        taskRepository.save(task)
    }

    @Transactional
    fun removeTask(task: Task, story: Story) {
        if (!taskExists(task) || !storyExists(story)) {
            throw IllegalArgumentException("Task or Story does not exist")
        }
        story.removeTask(task)
        storyRepository.save(story)
        taskRepository.delete(task)
    }

    fun taskExists(task: Task): Boolean {
        return !taskRepository.findById(task.id!!).isEmpty
    }

    fun storyExists(story: Story): Boolean {
        return !storyRepository.findById(story.id!!).isEmpty
    }
}