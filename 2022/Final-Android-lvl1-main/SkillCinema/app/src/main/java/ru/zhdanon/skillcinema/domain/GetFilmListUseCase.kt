package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import ru.zhdanon.skillcinema.data.ParamsFilterFilm
import ru.zhdanon.skillcinema.data.filmbyfilter.FilmByFilter
import ru.zhdanon.skillcinema.data.filmbyfilter.ResponseByFilter
import javax.inject.Inject

class GetFilmListUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeFilmsByFilter(
        filters: ParamsFilterFilm,
        page: Int
    ): List<FilmByFilter> {
        return repository.getFilmsByFilter(
            filters = filters,
            page = page
        ).items
    }

    suspend fun executeFilmsByFilterCount(
        filters: ParamsFilterFilm,
        page: Int
    ): ResponseByFilter {
        return repository.getFilmsByFilter(filters, page)
    }
}