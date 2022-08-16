package com.example.hw_roomdao.presentation.project_with_employee

import android.content.Context
import androidx.lifecycle.*
import com.example.hw_roomdao.data.ProjectWithEmployeesRepository
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.Project
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectWithEmployeesListViewModel : ViewModel() {

    private val projectWithEmployeesRepository = ProjectWithEmployeesRepository()

    private val employeesListMutableLiveData =
        MutableLiveData<List<Employee>>()
    val projectWithEmployeesListLiveData: LiveData<List<Employee>>
        get() = employeesListMutableLiveData

    fun initExistedEmployees(requireContext: Context) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.initExistedEmployee(requireContext)
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
            }
        }
    }


    fun loadList(currentProjectId: Long) {
        viewModelScope.launch {
            try {
                employeesListMutableLiveData.postValue(
                    projectWithEmployeesRepository.getProjectWithEmployeeById(
                        currentProjectId
                    )
                )
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
                employeesListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeEmployeeFromCurrentProject(selectedEmployee: Employee, currentProject: Project) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.removeEmployeeInCurrentProject(selectedEmployee.employeeId)
                loadList(currentProject.projectId)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}