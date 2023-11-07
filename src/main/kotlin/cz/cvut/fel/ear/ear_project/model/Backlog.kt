package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Backlog : AbstractEntity() {
    @OneToOne(mappedBy = "backlog")
    lateinit var project: Project

    @OneToMany(mappedBy = "backlog")
    lateinit var stories : List<Story>
}
