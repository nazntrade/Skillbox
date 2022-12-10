package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import javax.inject.Inject

class GetGalleryByIdUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeGalleryByFilmId(filmId: Int, type: String, page: Int) =
        repository.getGalleryByFilmId(filmId, type, page)
}