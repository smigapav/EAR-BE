package cz.cvut.fel.ear.ear_project.service

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
) {
    fun createUser(name: String): User {
        val user = User()
        user.username = name
        userRepository.save(user)
        return user
    }

    fun removeUser(user: User) {
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        userRepository.delete(user)
    }

    @Transactional
    fun addTask(
        user: User,
        task: Task,
    ): User {
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
        user: User,
        task: Task,
    ) {
        if (!userExists(user) || !taskExists(task)) {
            throw IllegalArgumentException("User or task does not exist")
        }
        user.removeTask(task)
        userRepository.save(user)
        task.user = null
        taskRepository.save(task)
    }

    fun changeUsername(
        user: User,
        username: String,
    ): User {
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        user.username = username
        userRepository.save(user)
        return user
    }

    fun findAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun findUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun userExists(user: User): Boolean {
        return user.id != null && !userRepository.findById(user.id!!).isEmpty
    }

    fun taskExists(task: Task): Boolean {
        return task.id != null && !taskRepository.findById(task.id!!).isEmpty
    }
}
