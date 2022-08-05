package com.example.hw_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CompanyContract.TABLE_NAME)
data class Company(
    @PrimaryKey
    @ColumnInfo(name = CompanyContract.Columns.ID)
    val companyId: Long,
    @ColumnInfo(name = CompanyContract.Columns.COMPANY_NAME)
    val companyName: String
)
