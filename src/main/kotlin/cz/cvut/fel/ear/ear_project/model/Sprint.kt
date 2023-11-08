package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.*

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SPRINT_TYPE")
@Entity
abstract class Sprint : AbstractEntity() {
    abstract var name: String?

    abstract var project: Project?

    abstract var stories: MutableList<Story>?

    fun addStory(story: Story) {
        if (stories == null) {
            stories = mutableListOf()
        }
        stories!!.add(story)
    }

    fun removeStory(story: Story) {
        if (stories == null) {
            return
        }
        stories!!.remove(story)
    }
}
