package ru.ktsstudio.roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ktsstudio.roomdao.data.db.models.Purchase
import ru.ktsstudio.roomdao.data.db.models.User

@Database(
    entities = [
        User::class,
        Purchase::class
    ], version = SkillboxDatabase.DB_VERSION
)
abstract class SkillboxDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "skillbox-database"
    }
}