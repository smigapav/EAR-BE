package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tasks")
class Task(
    var taskState: TaskState = TaskState.WAITING,
    var timeSpent: Int = 0,
    @Basic(optional = false)
    var name: String? = null,
    @Basic(optional = false)
    var description: String? = null,
    @ManyToOne
    var story: Story? = null,
    @ManyToOne(optional = true)
    var user: User? = null,
) : AbstractEntity()
