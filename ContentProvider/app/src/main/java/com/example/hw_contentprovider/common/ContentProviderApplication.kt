package com.example.hw_contentprovider.common

import android.app.Application
import android.os.StrictMode
import com.example.hw_contentprovider.BuildConfig

class ContentProviderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeath()
                    .build()
            )
        }
    }
}
