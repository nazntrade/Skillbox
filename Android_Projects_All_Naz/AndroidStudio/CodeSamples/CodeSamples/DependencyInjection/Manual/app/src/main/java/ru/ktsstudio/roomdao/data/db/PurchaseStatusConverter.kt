package ru.ktsstudio.roomdao.data.db

import androidx.room.TypeConverter
import ru.ktsstudio.roomdao.data.db.models.PurchaseStatus

class PurchaseStatusConverter {

    @TypeConverter
    fun convertStatusToString(status: PurchaseStatus): String = status.name

    @TypeConverter
    fun convertStringToStatus(statusString: String): PurchaseStatus =
        PurchaseStatus.valueOf(statusString)
}