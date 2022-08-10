package com.example.hw_roomdao.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.withTransaction
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

    val existedCompany = Company((0..9999L).random(), "First Book")
    private val existedDirector =
        Director(directorName = "Sam North", companyId = existedCompany.companyId)
    private val existedProjects = listOf(
        Project((0..9999L).random(), "Project Synergy", existedCompany.companyId),
        Project((0..9999L).random(), "Project Zen", existedCompany.companyId),
        Project((0..9999L).random(), "Matadors", existedCompany.companyId),
        Project((0..9999L).random(), "Artificial intelligence", existedCompany.companyId),
        Project((0..9999L).random(), "Strava", existedCompany.companyId),
        Project((0..9999L).random(), "WhatsApp", existedCompany.companyId)
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
                    sharedPreferences.getBoolean("first_run", true)
                if (sharedPrefExistedValue) {
                    Log.d("first_run: ", "true")

                    // in order to insert all objects in ONE transaction
                    Database.instance.withTransaction {
                        companyDao.insertCompany(existedCompany)
                        directorDao.insertDirector(existedDirector)
                        projectDao.insertProject(existedProjects)
                    }

                    sharedPreferences.edit()
                        .putBoolean("first_run", false)
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