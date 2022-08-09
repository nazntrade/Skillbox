package com.example.hw_roomdao.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeePosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectWithEmployeesRepository {

    private val employeeDao = Database.instance.employeeDao()
    private val relationsDao = Database.instance.relationsDao()

    private lateinit var sharedPreferences: SharedPreferences

    private val existedEmployees = listOf(
        Employee(1, "Beff", "Jezos", "jezos@gmail.com", EmployeePosition.DESIGNER),
        Employee(2, "Sark", "Muckerberg", "muckerberg@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(3, "Gill", "Bates", "bates@gmail.com", EmployeePosition.TESTER),
        Employee(4, "Donny", "Jepp", "jepp@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(5, "Hom", "Tanks", "tanks@gmail.com", EmployeePosition.DESIGNER),
        Employee(6, "Don", "Yangon", "uianks@gmail.com", EmployeePosition.TESTER),
        Employee(7, "Mike", "Town", "thagnks@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(8, "Brandon", "Anderson", "pthvbnvbanks@gmail.com", EmployeePosition.TESTER),
        Employee(9, "Edward", "Graham", "otohbnkdanks@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(10, "Cameron", "Henderson", "uthfghcanks@gmail.com", EmployeePosition.DESIGNER),
        Employee(11, "Diana", "Kelly", "ctheertyanks@gmail.com", EmployeePosition.TESTER),
        Employee(12, "Andrew", "Becker", "jthanks@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(13, "Gordon", "McDonald", "trethanks@gmail.com", EmployeePosition.DESIGNER),
        Employee(14, "Harry", "Robertson", "yuthanks@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(15, "Emma", "Jones", "mnjthanks@gmail.com", EmployeePosition.PROGRAMMER),
        Employee(16, "Lohn", "Jennon", "Lojthanks@gmail.com", EmployeePosition.PROGRAMMER)
    )

    suspend fun removeEmployeeInCurrentProject(employeeId: Long) {
        return withContext(Dispatchers.IO) {
            relationsDao.removeEmployeeInCurrentProjectById(employeeId)
        }
    }

    suspend fun initExistedEmployee(requireContext: Context) {
        withContext(Dispatchers.IO) {
            sharedPreferences =
                requireContext.getSharedPreferences(
                    ProjectRepository.SHARED_PREFS_NAME,
                    Context.MODE_PRIVATE
                )
            try {
                val sharedPrefExistedEmployee =
                    sharedPreferences.getBoolean("existed_employees_first_run", true)
                if (sharedPrefExistedEmployee) {
                    Log.d("existed_employees: ", "created")

                    employeeDao.insertEmployee(existedEmployees)

                    sharedPreferences.edit()
                        .putBoolean("existed_employees_first_run", false)
                        .apply()
                }
            } catch (t: Throwable) {

            }
        }
    }

    suspend fun getProjectWithEmployeeById(projectWithEmployeeId: Long): List<Employee> {
        var listEmployees: List<Employee>
        withContext(Dispatchers.IO) {
            val projectWithEmployee = relationsDao.getProjectWithEmployeeById(projectWithEmployeeId)
            listEmployees = projectWithEmployee.employees
        }
        return listEmployees
    }
}