package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import ru.zhdanon.skillcinema.data.filmbyid.ResponseCurrentFilm
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeFilmById(filmId: Int): ResponseCurrentFilm {
        return repository.getFilmById(filmId)
    }
}