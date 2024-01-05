package cz.cvut.fel.ear.ear_project.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity
@Table(name = "stories")
class Story(
    @Basic(optional = false)
    var storyPoints: Int? = null,
    @Basic(optional = false)
    @Column(unique = true)
    var name: String? = null,
    @Basic(optional = false)
    var description: String? = null,
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    var project: Project? = null,
    @JsonManagedReference
    @OneToMany(mappedBy = "story", cascade = [CascadeType.REMOVE])
    var tasks: MutableList<Task> = mutableListOf(),
    @JsonBackReference
    @ManyToOne
    var sprint: AbstractSprint? = null,
) : AbstractEntity() {
    fun addTask(task: Task) {
        if (tasks.contains(task)) {
            throw ItemAlreadyPresentException("Task already present in story")
        }
        tasks.add(task)
    }

    fun removeTask(task: Task) {
        if (!tasks.contains(task)) {
            throw ItemNotFoundException("Task not found in story")
        }
        tasks.remove(task)
    }

    override fun toString(): String {
        return "Story(sotoryPoints=$storyPoints, name=$name, description=$description, projectName=${project?.name}, tasks=$tasks, sprintName=${sprint?.name})"
    }
}
