package com.example.hw_roomdao.presentation.employee_list

import androidx.lifecycle.*
import com.example.hw_roomdao.data.EmployeeRepository
import com.example.hw_roomdao.data.db.models.Employee
import kotlinx.coroutines.launch
import timber.log.Timber

class EmployeeListViewModel() : ViewModel() {

    private val contactsListRepository = EmployeeRepository()

    private val contactsListMutableLiveData = MutableLiveData<List<Employee>>()
    val employeeListLiveData: LiveData<List<Employee>>
        get() = contactsListMutableLiveData


    fun loadList() {
        viewModelScope.launch {
            try {
                contactsListMutableLiveData.postValue(contactsListRepository.getAllEmployee())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun addEmployeeToProjectList(selectedEmployee: Employee) {
        viewModelScope.launch {
            try {

            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeEmployee(employee: Employee) {
        viewModelScope.launch {
            try {
                contactsListRepository.removeEmployee(employee.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}