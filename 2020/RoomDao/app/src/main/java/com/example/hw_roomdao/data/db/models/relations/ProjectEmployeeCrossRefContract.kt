package com.example.hw_roomdao.data.db.models.relations

object ProjectEmployeeCrossRefContract {

    const val TABLE_NAME = "project_employee_cross_ref"

    object Columns {
        const val PROJECT_ID = "projectId"
        const val EMPLOYEE_ID = "employeeId"
    }
}