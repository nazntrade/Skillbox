package com.skillbox.m16_architecture.data

import android.content.Context
import com.skillbox.m16_architecture.data.network.Networking
import com.skillbox.m16_architecture.data.entity.UsefulActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BoredRepository(context: Context) {

    suspend fun getUsefulActivity(): UsefulActivity {
        return withContext(Dispatchers.IO) {
            Networking.boredApi.getUsefulActivity()
        }
    }
}