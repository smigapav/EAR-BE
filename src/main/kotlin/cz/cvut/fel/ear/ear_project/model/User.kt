package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
class User : AbstractEntity() {
    @Basic(optional = false)
    lateinit var username: String

    @Basic(optional = false)
    lateinit var webApiKey: String

    @OneToMany(mappedBy = "user")
    lateinit var tasks : List<Task>

    @ManyToMany(mappedBy = "users")
    lateinit var projects : List<Project>

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    lateinit var permissions: List<Permissions>
}
