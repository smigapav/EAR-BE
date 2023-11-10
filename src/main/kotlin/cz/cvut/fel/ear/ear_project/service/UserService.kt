package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.PermissionsRepository
import cz.cvut.fel.ear.ear_project.dao.ProjectRepository
import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val taskRepository: TaskRepository,
    @Autowired
    private val projectRepository: ProjectRepository,
    @Autowired
    private val permissionRepository: PermissionsRepository
) {
    @Transactional
    fun insertUser(user: User) {
        userRepository.save(user)
    }

    @Transactional
    fun removeUser(user: User) {
        userRepository.delete(user)
    }

    @Transactional
    fun addTask(user: User, task: Task) {
        user.addTask(task)
        userRepository.save(user)
        task.user = user
        taskRepository.save(task)
    }

    @Transactional
    fun removeTask(user: User, task: Task) {
        user.tasks.remove(task)
        userRepository.save(user)
        task.user = null
        taskRepository.save(task)
    }

    @Transactional
    fun changeUsername(user: User, username: String) {
        user.username = username
        userRepository.save(user)
    }

    fun findAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun findUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
}
