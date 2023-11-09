package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "SPRINT_TYPE")
@Entity
@Table(name = "sprints")
class Sprint : AbstractEntity() {

    var name: String? = null

    @ManyToOne
    var project: Project? = null

    @OneToMany(mappedBy = "sprint")
    var stories: MutableList<Story> = mutableListOf()

    fun addStory(story: Story) {
        if(stories == null) {
            stories = mutableListOf()
        }
        stories?.add(story)
    }

    fun removeStory(story: Story) {
        stories?.remove(story)
    }
}

