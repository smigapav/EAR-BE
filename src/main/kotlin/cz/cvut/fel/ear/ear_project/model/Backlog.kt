package cz.cvut.fel.ear.ear_project.model

import cz.cvut.fel.ear.ear_project.exceptions.ItemAlreadyPresentException
import cz.cvut.fel.ear.ear_project.exceptions.ItemNotFoundException
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
        if (stories.contains(story)) {
            throw ItemAlreadyPresentException("Story already present in backlog")
        }
        stories.add(story)
    }

    fun removeStory(story: Story) {
        if (!stories.contains(story)) {
            throw ItemNotFoundException("Story not found in backlog")
        }
        stories.remove(story)
    }
}
