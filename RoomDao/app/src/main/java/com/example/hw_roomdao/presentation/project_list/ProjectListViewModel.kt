package com.example.hw_roomdao.presentation.project_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.data.ProjectRepository
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectListViewModel : ViewModel() {

    private val projectRepository = ProjectRepository()

    private val projectListMutableLiveData = MutableLiveData<List<Project>>()

    val projectsLiveData: LiveData<List<Project>>
        get() = projectListMutableLiveData

    fun initExistedProjects(){
        viewModelScope.launch {
            try {
                projectRepository.initExistedProjects()
            } catch (t: Throwable) {
                Timber.e(t, "project list error")
            }
        }

    }

    fun loadList() {
        viewModelScope.launch {
            try {
                projectListMutableLiveData.postValue(projectRepository.getAllProjects())
            } catch (t: Throwable) {
                Timber.e(t, "project list error")
                projectListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeProject(project: Project) {
        viewModelScope.launch {
            try {
                projectRepository.removeProject(project.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}