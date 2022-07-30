package com.example.hw_roomdao.presentation.employee_list

import android.content.Context
import androidx.lifecycle.*
import com.example.hw_roomdao.data.EmployeeRepository
import com.example.hw_roomdao.data.db.models.Employee
import kotlinx.coroutines.launch
import timber.log.Timber

class EmployeeListViewModel() : ViewModel() {

    private val employeeListRepository = EmployeeRepository()

    private val employeeListMutableLiveData = MutableLiveData<List<Employee>>()
    val employeeListLiveData: LiveData<List<Employee>>
        get() = employeeListMutableLiveData

    fun initExistedEmployee(requireContext: Context) {
        viewModelScope.launch {
            try {
                employeeListRepository.initExistedEmployee(requireContext)
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
            }
        }
    }

    fun loadList() {
        viewModelScope.launch {
            try {
                employeeListMutableLiveData.postValue(employeeListRepository.getAllEmployee())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                employeeListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun addEmployeeToProjectList(selectedEmployee: Employee) {
        viewModelScope.launch {
            try {

            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                employeeListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeEmployee(employee: Employee) {
        viewModelScope.launch {
            try {
                employeeListRepository.removeEmployee(employee.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}