package com.example.hw_roomdao.data

import android.util.Patterns
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepository {

    private val employeeDao = Database.instance.employeeDao()

    private val existedEmployee = listOf(
        Employee(1, "Beff", "Jezos", "jezos@gmail.com"),
        Employee(2, "Mark", "Suckerberg", "suckerberg@gmail.com"),
        Employee(3, "Gill", "Bates", "bates@gmail.com"),
        Employee(4, "Donny", "Jepp", "jepp@gmail.com"),
        Employee(5, "Hom", "Tanks", "tanks@gmail.com"),
        Employee(6, "Don", "Yangon", "uianks@gmail.com"),
        Employee(7, "Mike", "Town", "thagnks@gmail.com"),
        Employee(8, "Brandon", "Anderson", "pthvbnvbanks@gmail.com"),
        Employee(9, "Edward", "Graham", "otohbnkdanks@gmail.com"),
        Employee(10, "Cameron", "Henderson", "uthfghcanks@gmail.com"),
        Employee(11, "Diana", "Kelly", "ctheertyanks@gmail.com"),
        Employee(12, "Andrew", "Becker", "jthanks@gmail.com"),
        Employee(13, "Gordon", "McDonald", "trethanks@gmail.com"),
        Employee(14, "Harry", "Robertson", "yuthanks@gmail.com"),
        Employee(15, "Emma", "Jones", "mnjthanks@gmail.com")
    )

    suspend fun saveEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            employeeDao.insertEmployee(listOf(employee))
        }

    }

    suspend fun updateEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            employeeDao.updateEmployee(employee)
        }
    }

    suspend fun removeEmployee(employeeId: Long) {
        return withContext(Dispatchers.IO) {
//            employeeDao.removeEmployeeById(employeeId)
        }
    }

    suspend fun getEmployeeById(employeeId: Long): Employee {
        return withContext(Dispatchers.IO) {
            employeeDao.getEmployeeById(employeeId)
        }
    }

    suspend fun getAllEmployee(): List<Employee> {
        return withContext(Dispatchers.IO) {
            employeeDao.insertEmployee(existedEmployee)
            employeeDao.getAllEmployee()
        }
    }

    private fun isEmployeeValid(employee: Employee): Boolean {
        return employee.firstName.isNotBlank() &&
                employee.lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(employee.email).matches()
    }

}
