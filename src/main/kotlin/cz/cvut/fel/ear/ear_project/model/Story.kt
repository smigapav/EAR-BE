package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity
@Table(name = "stories")
class Story(
    @Basic(optional = false)
    var price: Int? = null,
    @Basic(optional = false)
    var name: String? = null,
    @Basic(optional = false)
    var description: String? = null,
    @ManyToOne
    var project: Project? = null,
    @OneToMany(mappedBy = "story", cascade = [CascadeType.REMOVE])
    var tasks: MutableList<Task> = mutableListOf(),
    @ManyToOne
    var sprint: AbstractSprint? = null,
    @ManyToOne
    var backlog: Backlog? = null,
) : AbstractEntity() {
    fun addTask(task: Task) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        if (tasks.contains(task)) {
            throw ItemAlreadyPresentException("Task already present in story")
        }
        tasks.add(task)
    }

    fun removeTask(task: Task) {
        if (tasks == null) {
            return
        }
        if (!tasks.contains(task)) {
            throw ItemNotFoundException("Task not found in story")
        }
        tasks.remove(task)
    }

    override fun toString(): String {
        return "Story(price=$price, name=$name, description=$description, project=$project, tasks=$tasks, sprint=$sprint, backlog=$backlog)"
    }


}
