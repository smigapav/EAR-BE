package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
import jakarta.persistence.*

@Entity(name = "sprints")
@DiscriminatorColumn(name = "sprint_type", discriminatorType = DiscriminatorType.STRING)
abstract class Sprint(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,
    @Basic(optional = false)
    open var name: String? = null,
    @ManyToOne
    open var project: Project? = null,
    @OneToMany(mappedBy = "sprint")
    open var stories: MutableList<Story> = mutableListOf(),
) {
    fun addStory(story: Story) {
        if(stories == null) {
            stories = mutableListOf()
        }
        if (stories.contains(story)) {
            throw ItemAlreadyPresentException("Story already present in sprint")
        }
        stories?.add(story)
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
        return "Sprint(id=$id, name=$name, project=$project, stories=$stories)"
    }


}

