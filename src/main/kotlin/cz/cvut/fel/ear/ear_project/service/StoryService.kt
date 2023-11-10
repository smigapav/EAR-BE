package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.StoryRepository
import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.model.Story
import cz.cvut.fel.ear.ear_project.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoryService(
    @Autowired
    private val storyRepository: StoryRepository,
    @Autowired
    private val taskRepository: TaskRepository,
) {
    fun changePrice(story: Story, price: Int) {
        story.price = price
        storyRepository.save(story)
    }

    fun changeName(story: Story, name: String) {
        story.name = name
        storyRepository.save(story)
    }

    fun changeDescription(story: Story, description: String) {
        story.description = description
        storyRepository.save(story)
    }

    fun createTask(task: Task, story: Story) {
        task.story = story
        story.addTask(task)
        storyRepository.save(story)
        taskRepository.save(task)
    }

    fun removeTask(task: Task, story: Story) {
        story.removeTask(task)
        storyRepository.save(story)
        taskRepository.delete(task)
    }
}