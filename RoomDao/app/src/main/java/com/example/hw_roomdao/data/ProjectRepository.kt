package com.example.hw_roomdao.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository {

    private val projectDao = Database.instance.projectDao()

    private lateinit var sharedPreferences: SharedPreferences

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

    ///////////////////////////////////////////////////////////////
    suspend fun removeProject(projectId: Long) {
        withContext(Dispatchers.IO) {
            projectDao.removeProjectById(projectId)
        }
    }

//    suspend fun getProjectById(projectId: Long): Project? {
//        return projectDao.getProjectById(projectId)
//    }

    suspend fun initExistedProjects(requireContext: Context) {
        withContext(Dispatchers.IO) {
            sharedPreferences =
                requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            try {
                val sharedPrefExistedProjects = sharedPreferences.getBoolean("existed_projects_first_run", true)
                if (sharedPrefExistedProjects) {
                    Log.d("existed_projects: ", "created")

                    projectDao.insertProject(existedProjects)

                    sharedPreferences.edit()
                        .putBoolean("existed_projects_first_run", false)
                        .apply()
                }
            } catch (t: Throwable) {

            }
        }
    }

    suspend fun getAllProjects(): List<Project> {
        return withContext(Dispatchers.IO) {
            projectDao.getAllProject()
        }
    }

    companion object {
        const val SHARED_PREFS_NAME = "shared_pref"
    }
}