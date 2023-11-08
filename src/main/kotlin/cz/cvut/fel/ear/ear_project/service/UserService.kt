package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
) {
    @Transactional
    fun insertUser(user: User) {
        userRepository.save(user)
    }

    fun findAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun findUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
}
