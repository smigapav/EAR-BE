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
    fun changePrice(story: Story, price: Int): Story {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.price = price
        storyRepository.save(story)
        return story
    }

    fun changeName(story: Story, name: String): Story {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.name = name
        storyRepository.save(story)
        return story
    }

    fun changeDescription(story: Story, description: String): Story {
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        story.description = description
        storyRepository.save(story)
        return story
    }

    @Transactional
    fun createTask(name: String, description: String, story: Story): Task {
        val task = Task()
        task.name = name
        task.description = description
        if (!storyExists(story)) {
            throw IllegalArgumentException("Story does not exist")
        }
        task.story = story
        taskRepository.save(task)
        story.addTask(task)
        storyRepository.save(story)
        return task
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

    fun findAllStories(): List<Story> {
        return storyRepository.findAll()
    }

    fun findStoryById(id: Long): Story? {
        return storyRepository.findById(id).orElse(null)
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }

    fun storyExists(story: Story): Boolean {
        return story.id != null && !storyRepository.findById(story.id!!).isEmpty
    }

    fun toString(story: Story): String {
        return (story.id.toString() + " " + story.name + " " + story.description + " " + story.price)
    }
}