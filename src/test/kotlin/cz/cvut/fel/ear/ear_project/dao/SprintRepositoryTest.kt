package cz.cvut.fel.ear.ear_project.dao

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@Transactional
@DataJpaTest
class SprintRepositoryTest(
    @Autowired
    val sprintRepository: SprintRepository
) {
}