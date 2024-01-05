package cz.cvut.fel.ear.ear_project.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity(name = "sprints")
@DiscriminatorColumn(name = "sprint_type", discriminatorType = DiscriminatorType.STRING)
abstract class AbstractSprint(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,
    @Basic(optional = false)
    @Column(unique = true)
    open var name: String? = null,
    @JsonBackReference
    @ManyToOne
    open var project: Project? = null,
    @JsonManagedReference
    @OneToMany(mappedBy = "sprint")
    open var stories: MutableList<Story> = mutableListOf(),
    open var state: SprintState = SprintState.WAITING,
) {
    fun addStory(story: Story) {
        if (stories.contains(story)) {
            throw ItemAlreadyPresentException("Story already present in sprint")
        }
        stories.add(story)
    }

    fun removeStory(story: Story) {
        if (stories == null) {
            return
        }
        if (!stories.contains(story)) {
            throw ItemNotFoundException("Story not found in sprint")
        }
        stories.remove(story)
    }

    override fun toString(): String {
        return "Sprint(id=$id, name=$name, projectNmae=${project?.name}, stories=$stories)"
    }
}
