package com.example.hw_roomdao.presentation.project_with_employee

import android.content.Context
import androidx.lifecycle.*
import com.example.hw_roomdao.data.ProjectWithEmployeesRepository
import com.example.hw_roomdao.data.db.models.ProjectWithEmployee
import com.example.hw_roomdao.data.db.models.relations.ProjectWithEmployee
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectWithEmployeesListViewModel : ViewModel() {

    private val projectWithEmployeesRepository = ProjectWithEmployeesRepository()

    private val projectWithEmployeesListMutableLiveData = MutableLiveData<List<ProjectWithEmployee>>()
    val projectWithEmployeesListLiveData: LiveData<List<ProjectWithEmployee>>
        get() = projectWithEmployeesListMutableLiveData

    fun initExistedEmployees(requireContext: Context) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.initExistedEmployee(requireContext)
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
            }
        }
    }


    fun loadList() {
        viewModelScope.launch {
            try {
                projectWithEmployeesListMutableLiveData.postValue(projectWithEmployeesRepository.getAllEmployeeInCurrentProject())
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
                projectWithEmployeesListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeEmployee(projectWithEmployee: com.example.hw_roomdao.data.db.models.ProjectWithEmployee) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.removeEmployeeInCurrentProject(projectWithEmployee.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}