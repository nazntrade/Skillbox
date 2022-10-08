package com.example.hw_roomdao.app

import android.app.Application
import com.example.hw_roomdao.data.db.Database
import timber.log.Timber

class SkillboxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}