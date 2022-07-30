package com.example.hw_roomdao.presentation.employee_list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.EmployeeRepository
import com.example.hw_roomdao.data.IncorrectFormException
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class AddEmployeeViewModel : ViewModel() {

    private val employeeRepository = EmployeeRepository()

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    private val existingEmployeeMutableLiveData = MutableLiveData<Employee>()

    val existingEmployeeLiveData: LiveData<Employee?>
        get() = existingEmployeeMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent

    fun init(id: Long) {
        viewModelScope.launch {
            try {
                val user = employeeRepository.getEmployeeById(id)
                existingEmployeeMutableLiveData.postValue(user)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun save(
        id: Long,
        firstName: String,
        lastName: String,
        email: String,
    ) {

        val employee = Employee(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    employeeRepository.saveEmployee(employee)
                } else {
                    employeeRepository.updateEmployee(employee)
                }
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "user save error")
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.user_add_error_default
            }
        )
    }
}