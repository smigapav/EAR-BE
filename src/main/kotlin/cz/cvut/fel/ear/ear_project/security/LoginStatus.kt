package cz.cvut.fel.ear.ear_project.security

class LoginStatus {
    var isLoggedIn = false
    var username: String? = null
    var errorMessage: String? = null
    var isSuccess = false

    constructor()
    constructor(loggedIn: Boolean, success: Boolean, username: String?, errorMessage: String?) {
        isLoggedIn = loggedIn
        this.username = username
        this.errorMessage = errorMessage
        isSuccess = success
    }
}
