// package cz.cvut.fel.ear.ear_project.service
//
// import cz.cvut.fel.ear.ear_project.dao.SprintRepository
// import cz.cvut.fel.ear.ear_project.model.Sprint
//
// class SprintService(private val sprintRepository: SprintRepository) {
//
// //    fun createUnstartedSprint(){
// //        val unstartedSprint = UnstartedSprint()
// //        sprintRepository.save(unstartedSprint)
// //    }
//
//    fun changeSprintName(
//        sprintId: String,
//        name: String,
//    ) {
//        val sprint = sprintRepository.findById(sprintId)
//        if (sprint != null) {
//            sprint.name = name
//            sprintRepository.save(sprint)
//        }
//    }
// }
