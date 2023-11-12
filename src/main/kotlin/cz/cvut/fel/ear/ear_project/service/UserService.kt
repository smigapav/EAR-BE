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
    fun insertUser(user: User) {
        userRepository.save(user)
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
    ) {
        user.addTask(task)
        userRepository.save(user)
        task.user = user
        taskRepository.save(task)
    }

    @Transactional
    fun removeTask(
        user: User,
        task: Task,
    ) {
        user.tasks.remove(task)
        userRepository.save(user)
        task.user = null
        taskRepository.save(task)
    }

    fun changeUsername(
        user: User,
        username: String,
    ) {
        if (!userExists(user)) {
            throw IllegalArgumentException("User does not exist")
        }
        user.username = username
        userRepository.save(user)
    }

    fun findAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun findUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun userExists(user: User): Boolean {
        return !userRepository.findById(user.id!!).isEmpty
    }
}
