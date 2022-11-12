package com.skillbox.m16_architecture.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BoredRepository(context: Context) {

    suspend fun getEntertainment(): Entertainment {
        return withContext(Dispatchers.IO) {
            Networking.boredApi.getEntertainment()
        }
    }

}