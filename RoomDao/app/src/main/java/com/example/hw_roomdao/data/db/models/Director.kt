package com.example.hw_roomdao.data.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DirectorContract.TABLE_NAME)
data class Director(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DirectorContract.Columns.ID)
    val directorId: Long = (0..999999999999).random(),
    @ColumnInfo(name = DirectorContract.Columns.DIRECTOR_NAME)
    val directorName: String
) : Parcelable
