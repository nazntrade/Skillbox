package ru.ktsstudio.roomdao.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.ktsstudio.roomdao.data.db.Database
import timber.log.Timber

@HiltAndroidApp
class SkillboxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}