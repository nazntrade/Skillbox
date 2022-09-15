package com.skillbox.hw_scopedstorage.app

import android.app.Application
import com.skillbox.hw_scopedstorage.BuildConfig
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}