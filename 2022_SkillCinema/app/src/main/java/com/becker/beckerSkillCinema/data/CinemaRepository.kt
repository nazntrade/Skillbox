package com.becker.beckerSkillCinema.data

import com.becker.beckerSkillCinema.data.filmbyfilter.ResponseByFilter
import com.becker.beckerSkillCinema.data.filmspremier.FilmPremier
import com.becker.beckerSkillCinema.data.network.Networking
import com.becker.beckerSkillCinema.entity.HomeItem
import javax.inject.Inject

class CinemaRepository @Inject constructor() {

    // FragmentHome
    suspend fun getFilmsTop(topType: String, page: Int): List<HomeItem> {
        return Networking.kinopoiskApi.getFilmsTop(type = topType, page = page).films
    }

    suspend fun getFilmsPremier(year: Int, month: String): List<FilmPremier> {
        return Networking.kinopoiskApi.getPremier(year, month).items
    }

    // FragmentSearch
    suspend fun getFilmsByFilter(filters: ParamsFilterFilm, page: Int): ResponseByFilter {
        return Networking.kinopoiskApi.getFilmsByFilter(
            countries = if (filters.countries.isNotEmpty()) filters.countries.keys.first().toString() else "",
            genres = if (filters.genres.isNotEmpty()) filters.genres.keys.first().toString() else "",
            order = filters.order,
            type = filters.type,
            ratingFrom = filters.ratingFrom,
            ratingTo = filters.ratingTo,
            yearFrom = filters.yearFrom,
            yearTo = filters.yearTo,
            imdbId = filters.imdbId,
            keyword = filters.keyword,
            page = page
        )
    }


    // FragmentFilmDetail


    // FragmentStaffDetail


}