package ru.ktsstudio.roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = UsersContract.TABLE_NAME,
    indices = [
        Index(UsersContract.Columns.FIRST_NAME)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UsersContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = UsersContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = UsersContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = UsersContract.Columns.EMAIL)
    val email: String,
    @ColumnInfo(name = UsersContract.Columns.AVATAR)
    val avatar: String?,
    @ColumnInfo(name = UsersContract.Columns.AGE, defaultValue = "0")
    val age: Int
)