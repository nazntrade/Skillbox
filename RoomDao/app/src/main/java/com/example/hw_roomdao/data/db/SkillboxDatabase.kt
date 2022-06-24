package com.example.hw_roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw_roomdao.data.db.models.Chat
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.data.db.models.Messages

@Database(
    entities = [
        Contact::class,
        Chat::class,
        Messages::class
    ], version = SkillboxDatabase.DB_VERSION
)
abstract class SkillboxDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_VERSION = 1
    }
}