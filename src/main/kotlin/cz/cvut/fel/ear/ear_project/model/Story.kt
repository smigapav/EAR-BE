package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Entity
data class Story(
    var price: Int = 0,

    @Basic(optional = false)
    var name: String? = null,

    @Basic(optional = false)
    var description: String? = null,

    @ManyToOne
    var project : Project? = null,

    @OneToMany(mappedBy = "story", cascade = [CascadeType.REMOVE])
    var tasks : MutableList<Task>? = null,

    @ManyToOne
    var sprint : Sprint? = null,

    @ManyToOne
    var backlog : Backlog? = null
) : AbstractEntity() {
    fun addTask(task: Task) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        tasks!!.add(task)
    }

    fun removeTask(task: Task) {
        if (tasks == null) {
            return
        }
        tasks!!.remove(task)
    }
}
