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
        taskName: String,
        taskState: TaskState,
    ): Task {
        val task = findByName(taskName)
        task.state = taskState
        taskRepository.save(task)
        return task
    }

    fun setTimeSpent(
        taskName: String,
        timeSpent: Int,
    ): Task {
        val task = findByName(taskName)
        task.timeSpent = timeSpent
        taskRepository.save(task)
        return task
    }

    fun changeName(
        taskName: String,
        name: String,
    ): Task {
        val task = findByName(taskName)
        task.name = name
        taskRepository.save(task)
        return task
    }

    fun changeDescription(
        taskName: String,
        description: String,
    ): Task {
        val task = findByName(taskName)
        task.description = description
        taskRepository.save(task)
        return task
    }

    fun findByName(name: String): Task {
        return taskRepository.findByName(name) ?: throw NoSuchElementException("Task with name $name not found")
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }
}
