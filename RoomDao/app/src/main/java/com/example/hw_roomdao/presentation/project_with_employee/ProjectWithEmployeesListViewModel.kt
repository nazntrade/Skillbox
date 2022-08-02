package com.example.hw_roomdao.presentation.project_with_employee

import android.content.Context
import androidx.lifecycle.*
import com.example.hw_roomdao.data.ProjectWithEmployeesRepository
import com.example.hw_roomdao.data.db.models.Employee
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


    fun loadList(projectWithEmployeeId: Long) {
        viewModelScope.launch {
            try {
                employeesListMutableLiveData.postValue(projectWithEmployeesRepository.getProjectWithEmployeeById(projectWithEmployeeId))
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
                employeesListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeEmployee(selectedProjectWithEmployeeId: Employee) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.removeEmployeeInCurrentProject(selectedProjectWithEmployeeId.employeeId)
                loadList(selectedProjectWithEmployeeId.employeeId)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}