package cz.cvut.fel.ear.ear_project.dao

import cz.cvut.fel.ear.ear_project.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository

interface ProjectRepository : JpaRepository<Project, Long>