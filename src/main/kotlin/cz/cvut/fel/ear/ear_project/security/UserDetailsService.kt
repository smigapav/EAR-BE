package cz.cvut.fel.ear.ear_project.security

import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User =
            userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User '$username' not found")

        return UserDetails(user)
    }
}
