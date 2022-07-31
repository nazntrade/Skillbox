package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract
import com.example.hw_roomdao.data.db.models.relations.ProjectEmployeeCrossRef
import com.example.hw_roomdao.data.db.models.relations.ProjectWithEmployee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: List<Employee>)

    @Query("SELECT * FROM ${EmployeeContract.TABLE_NAME}")
    fun getAllEmployee(): List<Employee>

    @Update
    fun updateEmployee(employee: Employee)

    @Delete
    fun removeEmployee(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSelectedEmployeeToCurrentProject(crossRef: ProjectEmployeeCrossRef)

    @Transaction
    @Query("SELECT * FROM projects")
    suspend fun getEmployeeOfCurrentProject(): List<ProjectWithEmployee>////////////////////

//    @Query("DELETE FROM ${EmployeeContract.TABLE_NAME} WHERE ${EmployeeContract.Columns.ID} = :employeeId")
//    fun removeEmployeeById(employeeId: Long)

    @Query("SELECT * FROM ${EmployeeContract.TABLE_NAME} WHERE ${EmployeeContract.Columns.ID} = :employeeId")
    fun getEmployeeById(employeeId: Long): Employee

}