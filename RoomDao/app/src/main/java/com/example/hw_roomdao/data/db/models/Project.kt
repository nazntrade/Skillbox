package com.example.hw_roomdao.data.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = ProjectContract.TABLE_NAME)
data class Project(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectContract.Columns.ID)
    val projectId: Long = (150..9999L).random(),
    @ColumnInfo(name = ProjectContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = CompanyContract.Columns.ID)
    val companyId: Long
) : Parcelable