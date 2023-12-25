package cz.cvut.fel.ear.ear_project.model

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
    @ManyToOne(fetch = FetchType.EAGER)
    var story: Story? = null,
    @ManyToOne(optional = true)
    var user: User? = null,
) : AbstractEntity() {
    //    override fun toString(): String {
//        return "Task(state=$state, timeSpent=$timeSpent, name=$name, description=$description, story=$story, user=$user)"
//    }
}
