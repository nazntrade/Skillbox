package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import ru.zhdanon.skillcinema.data.filmbyfilter.ResponseGenresCountries
import javax.inject.Inject

class GetGenresCountriesUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeGenresCountries(): ResponseGenresCountries {
        return repository.getGenresCountries()
    }
}