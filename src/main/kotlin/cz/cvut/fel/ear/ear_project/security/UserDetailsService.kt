import cz.cvut.fel.ear.ear_project.dao.UserRepository
import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.kbss.ear.eshop.security.model.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User '$username' not found")
        return UserDetails(user)
    }
}