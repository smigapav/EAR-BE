package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
@DiscriminatorValue("UNSTARTED")
data class UnstartedSprint(
    @Basic(optional = false)
    override var name: String? = null,
    @ManyToOne
    override var project: Project? = null,
    @OneToMany(mappedBy = "sprint")
    override var stories: MutableList<Story>? = null,
) : Sprint()
