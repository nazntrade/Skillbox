package com.example.hw_roomdao.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository {

    private val projectDao = Database.instance.projectDao()
    private val companyDao = Database.instance.companyDao()
    private val directorDao = Database.instance.directorDao()

    private lateinit var sharedPreferences: SharedPreferences

    private val existedProjects = listOf(
        Project(1, "Project Synergy"),
        Project(2, "Project Zen"),
        Project(3, "Matadors"),
        Project(4, "Artificial intelligence"),
        Project(5, "Strava"),
        Project(6, "WhatsApp")
    )
    private val existedCompany = Company(1, "First Book")
    private val existedDirector = Director(directorName = "Sam North")

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
        withContext(Dispatchers.IO) {
            projectDao.removeProjectById(projectId)
        }
    }

    suspend fun initExistedCompanyWithDirectorWithProjects(requireContext: Context) {
        withContext(Dispatchers.IO) {
            sharedPreferences =
                requireContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            try {
                val sharedPrefExistedValue =
                    sharedPreferences.getBoolean("existedValue_first_run", true)
                if (sharedPrefExistedValue) {
                    Log.d("ExistedValue_first_run: ", "created")

                    companyDao.insertCompany(existedCompany)
                    projectDao.insertProject(existedProjects)
                    directorDao.insertDirector(existedDirector)

                    sharedPreferences.edit()
                        .putBoolean("existedValue_first_run", false)
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