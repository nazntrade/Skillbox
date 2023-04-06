package com.becker.beckerSkillCinema

import android.app.Application
import android.os.StrictMode
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this // in order to get strings from resources anywhere class MyStrings

        FirebaseCrashlytics.getInstance()
            .setCrashlyticsCollectionEnabled(/*!BuildConfig.DEBUG*/true)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    companion object {
        // in order to get strings from resources anywhere class MyStrings
        lateinit var instance: App private set
    }
}