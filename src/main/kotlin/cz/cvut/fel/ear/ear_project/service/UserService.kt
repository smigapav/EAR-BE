package cz.cvut.fel.ear.ear_project.service

import cz.cvut.fel.ear.ear_project.model.User

class UserService {
    fun changeUserName(
        user : User,
        name : String) {
        user.username = name
    }
}