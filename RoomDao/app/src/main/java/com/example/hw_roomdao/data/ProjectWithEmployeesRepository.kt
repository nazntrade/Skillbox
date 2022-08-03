package com.example.hw_roomdao.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectWithEmployeesRepository {

    private val employeeDao = Database.instance.employeeDao()
    private val relationsDao = Database.instance.relationsDao()

    private lateinit var sharedPreferences: SharedPreferences

    private val existedEmployees = listOf(
        Employee(1, "Beff", "Jezos", "jezos@gmail.com"),
        Employee(2, "Sark", "Muckerberg", "muckerberg@gmail.com"),
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
        Employee(15, "Emma", "Jones", "mnjthanks@gmail.com"),
        Employee(16, "Lohn", "Jennon", "Lojthanks@gmail.com")
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