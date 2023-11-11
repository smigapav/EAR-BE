package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.RunningSprint
import cz.cvut.fel.ear.ear_project.model.Sprint
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SprintRepository : JpaRepository<Sprint, Long> {
    fun findByName(name: String): Sprint?
    fun findByProjectName(projectName: String): List<Sprint>

    @Modifying
    @Transactional
    @Query("UPDATE sprints o SET o.sprint_type = 'RUNNING' WHERE o.id = :id", nativeQuery = true)
    fun changeTypeToRunning(@Param("id") id: Long)

    @Query("SELECT o.sprint_type FROM sprints o WHERE o.id = :id", nativeQuery = true)
    fun findSprintTypeById(@Param("id") id: Long): String?

    //fun findByIdRunningSprint(id: Long): RunningSprint?

    /*
    @Query("SELECT o FROM sprints o WHERE o.sprint_type = :sprintType")
    fun findAllSprints(@Param("sprint_type")sprintType: String): String?

    @Query("SELECT s FROM sprints s WHERE s.sprint_type = :sprintType")
    fun findBySprintType(sprintType: String): List<Sprint>?

     */
}
