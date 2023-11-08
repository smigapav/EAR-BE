package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
data class Backlog(
    @OneToOne(mappedBy = "backlog")
    var project: Project? = null,

    @OneToMany(mappedBy = "backlog")
    var stories : MutableList<Story>? = null
) : AbstractEntity() {
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
