//package cz.cvut.fel.ear.ear_project.model
//
//import jakarta.persistence.*
//
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "SPRINT_TYPE")
//@Table(name = "Sprints")
//abstract class Sprint : AbstractEntity() {
//
//    @Column
//    open var name: String? = null
//
//    @ManyToOne
//    open var project: Project? = null
//
//    @OneToMany(mappedBy = "sprint")
//    open var stories: MutableList<Story>? = null
//
//    fun addStory(story: Story) {
//        if(stories == null) {
//            stories = mutableListOf()
//        }
//        stories?.add(story)
//    }
//
//    fun removeStory(story: Story) {
//        stories?.remove(story)
//    }
//}
//
