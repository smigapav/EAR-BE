package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.model.State
import cz.cvut.fel.ear.ear_project.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService(
    @Autowired
    private val taskRepository: TaskRepository,
) {
    fun changeState(task: Task, state: State) {
        task.state = state
        taskRepository.save(task)
    }

    fun setTimeSpent(task: Task, timeSpent: Int) {
        task.timeSpent = timeSpent
        taskRepository.save(task)
    }

    fun changeName(task: Task, name: String) {
        task.name = name
        taskRepository.save(task)
    }

    fun changeDescription(task: Task, description: String) {
        task.description = description
        taskRepository.save(task)
    }

    fun findAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    fun findTaskById(id: Long): Task? {
        return taskRepository.findById(id).orElse(null)
    }
}