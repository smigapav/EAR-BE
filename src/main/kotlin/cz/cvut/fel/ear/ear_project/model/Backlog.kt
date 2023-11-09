package cz.cvut.fel.ear.ear_project.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "backlogs")
data class Backlog(
    @OneToOne(mappedBy = "backlog")
    var project: Project? = null,
    @OneToMany(mappedBy = "backlog")
    var stories: MutableList<Story> = mutableListOf(),
) : AbstractEntity() {
    fun addStory(story: Story) {
        stories.add(story)
    }

    fun removeStory(story: Story) {
        stories.remove(story)
    }
}
