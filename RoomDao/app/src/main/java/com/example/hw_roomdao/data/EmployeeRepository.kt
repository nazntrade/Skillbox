package com.example.hw_roomdao.data

import android.util.Patterns
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.data.db.models.relations.ProjectEmployeeCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepository {

    private val employeeDao = Database.instance.employeeDao()

    suspend fun saveEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            employeeDao.insertEmployee(listOf(employee))
        }
    }

    suspend fun addSelectedEmployeeToCurrentProject(
        currentProject: Project,
        selectedEmployee: Employee
    ) {
        return withContext(Dispatchers.IO) {
            employeeDao.insertSelectedEmployeeToCurrentProject(
                ProjectEmployeeCrossRef(
                    currentProject.projectId,
                    selectedEmployee.employeeId
                )
            )
        }
    }

    suspend fun updateEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            employeeDao.updateEmployee(employee)
        }
    }

////////////////////////////////////////////////////////////////////////
    suspend fun removeEmployee(employeeId: Long) {
        return withContext(Dispatchers.IO) {
            employeeDao.removeEmployeeById(employeeId)
        }
    }

    suspend fun getEmployeeById(employeeId: Long): Employee {
        return withContext(Dispatchers.IO) {
            employeeDao.getEmployeeById(employeeId)
        }
    }

    suspend fun getAllEmployee(): List<Employee> {
        return withContext(Dispatchers.IO) {
            employeeDao.getAllEmployee()
        }
    }

    private fun isEmployeeValid(employee: Employee): Boolean {
        return employee.firstName.isNotBlank() &&
                employee.lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(employee.email).matches()
    }
}
