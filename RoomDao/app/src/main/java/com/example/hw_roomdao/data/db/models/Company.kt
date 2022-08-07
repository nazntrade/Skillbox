package com.example.hw_roomdao.data.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = CompanyContract.TABLE_NAME)
data class Company(
    @PrimaryKey
    @ColumnInfo(name = CompanyContract.Columns.ID)
    val companyId: Long,
    @ColumnInfo(name = CompanyContract.Columns.COMPANY_NAME)
    val companyName: String
) : Parcelable
