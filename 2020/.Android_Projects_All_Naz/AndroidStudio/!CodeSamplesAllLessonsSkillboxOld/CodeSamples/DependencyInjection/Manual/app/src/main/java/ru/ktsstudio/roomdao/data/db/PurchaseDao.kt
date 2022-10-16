package ru.ktsstudio.roomdao.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.ktsstudio.roomdao.data.db.models.Purchase

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchases(purchases: List<Purchase>)

}