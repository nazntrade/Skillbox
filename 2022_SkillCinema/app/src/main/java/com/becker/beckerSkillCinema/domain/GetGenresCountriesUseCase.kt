package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.filmByFilter.ResponseGenresCountries
import javax.inject.Inject

class GetGenresCountriesUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeGenresCountries(): ResponseGenresCountries {
        return repository.getGenresCountries()
    }
}