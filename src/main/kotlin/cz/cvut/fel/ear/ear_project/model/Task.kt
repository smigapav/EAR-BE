package cz.cvut.fel.ear.ear_project.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class Task(
    var state: TaskState = TaskState.WAITING,
    var timeSpent: Int = 0,
    @Basic(optional = false)
    @Column(unique = true)
    var name: String? = null,
    @Basic(optional = false)
    var description: String? = null,
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    var story: Story? = null,
    @JsonBackReference
    @ManyToOne(optional = true)
    var user: User? = null,
) : AbstractEntity() {

    override fun toString(): String {
        return "Task(state=$state, timeSpent=$timeSpent, name=$name, description=$description, storyName=${story?.name}, userName=${user?.username})"
    }
}
