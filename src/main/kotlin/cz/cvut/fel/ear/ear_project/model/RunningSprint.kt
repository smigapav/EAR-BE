package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("RUNNING")
data class RunningSprint(
    @Basic(optional = false)
    override var name: String? = null,
    @ManyToOne
    override var project: Project? = null,
    @OneToMany(mappedBy = "sprint")
    override var stories: MutableList<Story>? = null,
    var start: LocalDateTime? = null,
) : Sprint() {
    fun setStart() {
        start = LocalDateTime.now()
    }
}
