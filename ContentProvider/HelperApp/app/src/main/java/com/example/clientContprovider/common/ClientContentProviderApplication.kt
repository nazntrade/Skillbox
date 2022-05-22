package com.example.clientContprovider.common

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.example.clientContprovider.BuildConfig
import java.lang.IllegalStateException

class ClientContentProviderApplication : Application() {

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


