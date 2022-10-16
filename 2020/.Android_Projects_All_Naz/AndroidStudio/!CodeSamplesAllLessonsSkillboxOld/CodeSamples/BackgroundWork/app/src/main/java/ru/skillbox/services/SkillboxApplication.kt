package ru.skillbox.services

import android.app.Application
import timber.log.Timber

class SkillboxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NotificationChannels.create(this)
    }

}