package com.skillbox.m16_architecture.data

import com.skillbox.m16_architecture.entity.UsefulActivity
import javax.inject.Inject

class BoredRepository @Inject constructor(
    private val usefulActivityDataSource: UsefulActivityDataSource
) {
    suspend fun getUsefulActivity(): UsefulActivity {
        return usefulActivityDataSource.loadActivity()
    }
}