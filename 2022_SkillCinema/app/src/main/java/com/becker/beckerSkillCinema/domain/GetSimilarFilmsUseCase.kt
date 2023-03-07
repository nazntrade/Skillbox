package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import com.becker.beckerSkillCinema.data.similarFilm.ResponseSimilarFilms
import javax.inject.Inject

class GetSimilarFilmsUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeSimilarFilms(filmId: Int): ResponseSimilarFilms {
        return repository.getSimilarByFilmId(filmId)
    }
}