package com.example.hw_roomdao.presentation.project_with_employee

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


    fun loadList() {
        viewModelScope.launch {
            try {
                projectWithEmployeesListMutableLiveData.postValue(projectWithEmployeesRepository.getAllEmployee())
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
                projectWithEmployeesListMutableLiveData.postValue(emptyList())
            }
        }
    }

    //    fun addEmployeeToProject(selectedEmployee: Employee) {
//        viewModelScope.launch {
//            try {
//
//            } catch (t: Throwable) {
//                Timber.e(t, "user list error")
//                projectWithEmployeesListMutableLiveData.postValue(emptyList())
//            }
//        }
//    }
//
    fun removeEmployee(employee: Employee) {
        viewModelScope.launch {
            try {
                projectWithEmployeesRepository.removeEmployee(employee.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}