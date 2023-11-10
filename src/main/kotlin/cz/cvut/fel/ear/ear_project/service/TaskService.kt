package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.model.State
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService(
    @Autowired
    private val taskRepository: TaskRepository,
) {
    fun changeState(task: Task, state: State) {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.state = state
        taskRepository.save(task)
    }

    fun setTimeSpent(task: Task, timeSpent: Int) {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.timeSpent = timeSpent
        taskRepository.save(task)
    }

    fun changeName(task: Task, name: String) {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.name = name
        taskRepository.save(task)
    }

    fun changeDescription(task: Task, description: String) {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.description = description
        taskRepository.save(task)
    }

    fun findAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    fun findTaskById(id: Long): Task? {
        return taskRepository.findById(id).orElse(null)
    }

    fun taskExists(task: Task): Boolean {
        return !taskRepository.findById(task.id!!).isEmpty
    }
}