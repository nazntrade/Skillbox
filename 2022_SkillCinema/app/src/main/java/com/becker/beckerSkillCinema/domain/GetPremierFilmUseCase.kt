package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.entity.HomeItem
import javax.inject.Inject

class GetPremierFilmUseCase @Inject constructor(
    private val repository: CinemaRepository
) {
    suspend fun executePremieres(year: Int, month: String): List<HomeItem> {
        return repository.getFilmsPremier(year, month)
    }
}