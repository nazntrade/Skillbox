package com.example.hw_roomdao.data.db

import androidx.room.TypeConverter
import com.example.hw_roomdao.data.db.models.EmployeePosition

class EmployeePositionConverter {

    @TypeConverter
    fun convertPositionToSting(position: EmployeePosition): String = position.name

    @TypeConverter
    fun convertStringToPosition(positionString: String): EmployeePosition =
        EmployeePosition.valueOf(positionString)
}