package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.TaskState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService(
    @Autowired
    private val taskRepository: TaskRepository,
) {
    fun changeState(
        task: Task,
        taskState: TaskState,
    ): Task {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.state = taskState
        taskRepository.save(task)
        return task
    }

    fun setTimeSpent(
        task: Task,
        timeSpent: Int,
    ): Task {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.timeSpent = timeSpent
        taskRepository.save(task)
        return task
    }

    fun changeName(
        task: Task,
        name: String,
    ): Task {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.name = name
        taskRepository.save(task)
        return task
    }

    fun changeDescription(
        task: Task,
        description: String,
    ): Task {
        if (!taskExists(task)) {
            throw IllegalArgumentException("Task does not exist")
        }
        task.description = description
        taskRepository.save(task)
        return task
    }

    fun findAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    fun findTaskById(id: Long): Task? {
        return taskRepository.findById(id).orElse(null)
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }
}
