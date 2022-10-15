package ru.ktsstudio.roomdao.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: SkillboxDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            SkillboxDatabase::class.java,
            SkillboxDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}