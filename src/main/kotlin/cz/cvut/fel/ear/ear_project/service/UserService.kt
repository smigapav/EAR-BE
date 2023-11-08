package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.dao.SprintRepository
import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.User

class UserService(private val userRepository: UserRepository) {
    fun changeUserName(
        user: User,
        name: String,
    ) {
        val tmpUser = userRepository.findByUsername(user.username)
        if (tmpUser != null) {
            tmpUser.username = name
        }
    }
}
