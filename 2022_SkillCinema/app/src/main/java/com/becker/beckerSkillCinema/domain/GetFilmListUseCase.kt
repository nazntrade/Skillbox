package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.ParamsFilterFilm
import com.becker.beckerSkillCinema.data.filmbyfilter.FilmByFilter
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

}