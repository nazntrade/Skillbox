package ru.ktsstudio.roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ktsstudio.roomdao.data.db.PurchaseStatusConverter

@Entity(
    tableName = PurchaseContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [UsersContract.Columns.ID],
            childColumns = [PurchaseContract.Columns.USER_ID]
        )
    ]
)
@TypeConverters(PurchaseStatusConverter::class)
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PurchaseContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = PurchaseContract.Columns.USER_ID)
    val userId: Long,
    @ColumnInfo(name = PurchaseContract.Columns.PRICE)
    val price: Int,
    @ColumnInfo(name = PurchaseContract.Columns.STATUS)
    val status: PurchaseStatus
)