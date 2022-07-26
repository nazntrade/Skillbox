package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.data.db.models.EmployeeContract

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

    @Query("DELETE FROM ${EmployeeContract.TABLE_NAME} WHERE ${EmployeeContract.Columns.ID} = :employeeId")
    fun removeEmployeeById(employeeId: Long)


    @Query("SELECT * FROM ${EmployeeContract.TABLE_NAME} WHERE ${EmployeeContract.Columns.ID} = :employeeId")
    fun getEmployeeById(employeeId: Long): Employee

}