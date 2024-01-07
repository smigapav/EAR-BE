package cz.cvut.fel.ear.ear_project.security

import cz.cvut.fel.ear.ear_project.model.User
import cz.cvut.fel.ear.ear_project.security.PermissionsUtils.Companion.addUserRoles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetails : UserDetails {
    private val user: User
    private val authorities: MutableSet<GrantedAuthority>

    constructor(user: User) {
        Objects.requireNonNull(user)
        this.user = user
        authorities = HashSet()
        addUserRoles(user, this.authorities)
    }

    constructor(user: User, authorities: Collection<GrantedAuthority>) {
        Objects.requireNonNull<Any>(user)
        Objects.requireNonNull(authorities)
        this.user = user
        this.authorities = HashSet()
        addUserRoles(user, this.authorities)
        this.authorities.addAll(authorities)
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.unmodifiableCollection(authorities)
    }

    override fun getPassword(): String {
        return user.password!!
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
