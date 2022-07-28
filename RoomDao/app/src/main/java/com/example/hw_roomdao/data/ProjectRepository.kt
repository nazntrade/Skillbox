package com.example.hw_roomdao.data

import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository {

    private val projectDao = Database.instance.projectDao()

    private val existedProjects = listOf(
        Project(1, "Project Synergy"),
        Project(2, "Project Zen"),
        Project(3, "Matadors"),
        Project(4, "Artificial intelligence")
    )

    suspend fun saveProject(project: Project) {
        withContext(Dispatchers.IO) {
            projectDao.insertProject(listOf(project))
        }
    }

    suspend fun updateProject(project: Project) {
        withContext(Dispatchers.IO) {
            projectDao.updateProject(project)
        }
    }

    suspend fun removeProject(projectId: Long) {
//        projectDao.removeProjectById(projectId)
    }

//    suspend fun getProjectById(projectId: Long): Project? {
//        return projectDao.getProjectById(projectId)
//    }

    suspend fun initExistedProjects() {
        withContext(Dispatchers.IO) {
            projectDao.insertProject(existedProjects)
        }
    }

    suspend fun getAllProjects(): List<Project> {
        return withContext(Dispatchers.IO) {
            projectDao.getAllProject()
        }
    }
}