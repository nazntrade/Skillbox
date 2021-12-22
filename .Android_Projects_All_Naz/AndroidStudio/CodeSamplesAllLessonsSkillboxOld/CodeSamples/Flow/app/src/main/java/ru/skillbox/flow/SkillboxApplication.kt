package ru.skillbox.flow

import android.app.Application
import timber.log.Timber

class SkillboxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}