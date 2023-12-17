package cz.cvut.kbss.ear.eshop.security.model

import cz.cvut.fel.ear.ear_project.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetails : UserDetails {
    private val user: User
    private val authorities: MutableSet<GrantedAuthority>

    constructor(user: User) {
        Objects.requireNonNull(user)
        this.user = user
        authorities = HashSet()
        addUserRoles()
    }

    constructor(user: User, authorities: Collection<GrantedAuthority>) {
        Objects.requireNonNull<Any>(user)
        Objects.requireNonNull(authorities)
        this.user = user
        this.authorities = HashSet()
        addUserRoles()
        this.authorities.addAll(authorities)
    }

    private fun addUserRoles() {
        user.projects.forEach { project ->
            val permission = project.permissions.find { it.user == user }
            if (permission != null) {
                if (permission.projectAdmin) {
                    val authority = "${project.id}admin"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
                if (permission.storiesAndTasksManager) {
                    val authority = "${project.id}manager"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
                if (permission.canManageSprints) {
                    val authority = "${project.id}sprints"
                    authorities.add(SimpleGrantedAuthority(authority))
                }
            }
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.unmodifiableCollection(authorities)
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return user.username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getUser(): User {
        return user
    }
}
