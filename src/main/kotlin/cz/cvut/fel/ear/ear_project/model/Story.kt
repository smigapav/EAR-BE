package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*


@Entity
class Story : AbstractEntity() {
    var price: Int = 0

    @Basic(optional = false)
    lateinit var name: String

    @Basic(optional = false)
    lateinit var description: String

    @ManyToOne
    lateinit var project: Project

    @OneToMany(mappedBy = "story", cascade = [CascadeType.REMOVE])
    lateinit var tasks: List<Task>

    @ManyToOne
    lateinit var sprint: Sprint

    @ManyToOne
    lateinit var backlog: Backlog
}
