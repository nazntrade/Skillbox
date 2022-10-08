package com.example.hw_roomdao.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Timber.d("migration from 1 to 2 start")
        database.execSQL("ALTER TABLE employee ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
        Timber.d("migration from 1 to 2 success")
    }
}