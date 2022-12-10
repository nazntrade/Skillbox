package com.skillbox.m16_architecture.domain

import com.skillbox.m16_architecture.data.BoredRepository
import com.skillbox.m16_architecture.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val boredRepository: BoredRepository
) {
    suspend fun execute(): UsefulActivity {
        return boredRepository.getUsefulActivity()
    }
}