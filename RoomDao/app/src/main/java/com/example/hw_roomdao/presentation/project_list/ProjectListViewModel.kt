package com.example.hw_roomdao.presentation.project_list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.data.EmployeeRepository
import com.example.hw_roomdao.data.ProjectRepository
import com.example.hw_roomdao.data.db.models.Company
import com.example.hw_roomdao.data.db.models.Director
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectListViewModel : ViewModel() {

    private val projectRepository = ProjectRepository()
    private val employeeRepository = EmployeeRepository()

    val currentCompany: Company
        get() = projectRepository.existedCompany

    private val projectListMutableLiveData = MutableLiveData<List<Project>>()
    val projectsLiveData: LiveData<List<Project>>
        get() = projectListMutableLiveData

    private val directorMutableLiveData = MutableLiveData<Director>()
    val directorLiveData: LiveData<Director>
        get() = directorMutableLiveData

    fun initExistedCompanyWithDirectorWithProjects(requireContext: Context) {
        viewModelScope.launch {
            try {
                projectRepository.initExistedCompanyWithDirectorWithProjects(requireContext)
                getDirector()
                loadList()
            } catch (t: Throwable) {
                Timber.e(t, "project list error")
            }
        }
    }

    private fun getDirector() {
        viewModelScope.launch {
            try {
                directorMutableLiveData.postValue(
                    employeeRepository.getDirector()
                )

            } catch (t: Throwable) {
                Timber.e(t, "get director error")
            }
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            try {
                projectListMutableLiveData.postValue(
                    projectRepository.getAllProjects()
                )
            } catch (t: Throwable) {
                Timber.e(t, "project list error")
                projectListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeProject(project: Project) {
        viewModelScope.launch {
            try {
                projectRepository.removeProject(project.projectId)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}