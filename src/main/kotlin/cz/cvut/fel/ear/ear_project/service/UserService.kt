package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.TaskRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.Project
import cz.cvut.fel.ear.ear_project.model.Task
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.security.SecurityUtils
import jakarta.persistence.OrderBy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val taskRepository: TaskRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private var securityUtils: SecurityUtils,
) {
    fun createUser(
        name: String,
        password: String,
    ): User {
        val user = User()
        user.username = name
        user.password = password
        user.encodePassword(passwordEncoder)
        userRepository.save(user)
        return user
    }

    fun removeUser() {
        val user = securityUtils.currentUser!!
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        userRepository.delete(user)
    }

    @Transactional
    fun addTask(
        taskName: String,
    ): User {
        val task = findTaskByName(taskName)
        val user = securityUtils.currentUser!!
        if (!userExists(user) || !taskExists(task)) {
            throw IllegalArgumentException("User or task does not exist")
        }
        user.addTask(task)
        task.user = user
        userRepository.save(user)
        taskRepository.save(task)
        return user
    }

    @Transactional
    fun removeTask(
        taskName: String,
    ) {
        val task = findTaskByName(taskName)
        val user = securityUtils.currentUser!!
        if (!userExists(user) || !taskExists(task)) {
            throw IllegalArgumentException("User or task does not exist")
        }
        user.removeTask(task)
        userRepository.save(user)
        task.user = null
        taskRepository.save(task)
    }

    fun changeUsername(username: String): User {
        val user = securityUtils.currentUser!!
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        user.username = username
        userRepository.save(user)
        return user
    }

    fun changePassword(password: String): User {
        val user = securityUtils.currentUser!!
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        user.password = password
        user.encodePassword(passwordEncoder)
        userRepository.save(user)
        return user
    }

    fun getCurrentUser(): User {
        val user = securityUtils.currentUser!!
        if (user == null) {
            throw IllegalArgumentException("User does not exist")
        }
        return user
    }

    fun findTaskByName(taskName: String): Task {
        return taskRepository.findByName(taskName) ?: throw NoSuchElementException("Task with $taskName not found")
    }

    fun userExists(user: User): Boolean {
        return user.id != null && !userRepository.findById(user.id!!).isEmpty
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }

    fun findUserProjects(user: User): List<Project> {
        return userRepository.findAllUsersProjects(user.id!!)
    }
}
