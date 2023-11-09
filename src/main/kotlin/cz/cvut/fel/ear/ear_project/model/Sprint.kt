package cz.cvut.fel.ear.ear_project.model

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
        stories?.add(story)
    }

    fun removeStory(story: Story) {
        stories?.remove(story)
    }

    override fun toString(): String {
        return "Sprint(id=$id, name=$name, project=$project, stories=$stories)"
    }


}

