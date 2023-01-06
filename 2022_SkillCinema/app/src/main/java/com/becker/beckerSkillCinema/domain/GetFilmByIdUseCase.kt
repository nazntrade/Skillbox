package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeFilmById(filmId: Int): ResponseCurrentFilm {
        return repository.getFilmById(filmId)
    }
}