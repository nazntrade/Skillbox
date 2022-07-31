package com.example.hw_roomdao.presentation.project_with_employee

import android.content.Context
import androidx.lifecycle.*
import com.example.hw_roomdao.data.ProjectWithEmployeesRepository
import com.example.hw_roomdao.data.db.models.Employee
import kotlinx.coroutines.launch
import timber.log.Timber

class ProjectWithEmployeesListViewModel : ViewModel() {

    private val projectWithEmployeesRepository = ProjectWithEmployeesRepository()

    private val projectWithEmployeesListMutableLiveData = MutableLiveData<List<Employee>>()
    val projectWithEmployeesListLiveData: LiveData<List<Employee>>
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

    fun removeEmployee(employee: Employee) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.removeEmployeeInCurrentProject(employee.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}