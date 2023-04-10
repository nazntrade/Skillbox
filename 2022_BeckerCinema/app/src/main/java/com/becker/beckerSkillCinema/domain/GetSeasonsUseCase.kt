package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeSeasons(seriesId: Int) = repository.getSeasonsById(seriesId)
}