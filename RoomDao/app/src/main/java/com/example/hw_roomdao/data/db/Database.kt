package com.example.hw_roomdao.data.db

import android.content.Context
import androidx.room.Room

//разобраться еще раз, как и зачем я все это сделал !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
object Database {

    lateinit var instance: SkillboxDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            SkillboxDatabase::class.java,
            SkillboxDatabase.DB_NAME
        ).build()
    }
}