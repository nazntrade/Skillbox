package com.becker.beckerSkillCinema.data

import com.becker.beckerSkillCinema.data.filmByFilter.ResponseByFilter
import com.becker.beckerSkillCinema.data.filmsPremier.FilmPremier
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
            countries = if (filters.countries.isNotEmpty()) filters.countries.keys.first() //????????????????????
                .toString() else "",
            genres = filters.genres,
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

    suspend fun getFilmById(filmId: Int) =
        Networking.kinopoiskApi.getCurrentFilm(filmId)

    suspend fun getSeasonsById(seriesId: Int) =
        Networking.kinopoiskApi.getSeasons(seriesId)

    suspend fun getActorsByFilmId(filmId: Int) =
        Networking.kinopoiskApi.getActors(filmId)

    suspend fun getGalleryByFilmId(filmId: Int, type: String, page: Int) =
        Networking.kinopoiskApi.getFilmImages(filmId, type, page)

    suspend fun getSimilarByFilmId(filmId: Int) =
        Networking.kinopoiskApi.getSimilarFilms(filmId)

    suspend fun getStaffById(staffId: Int) =
        Networking.kinopoiskApi.getStaff(staffId)

    fun putFilmId(filmId: Int) = DataCentre.putCurrentFilmId(filmId)

    fun getCurrentFilmId() = DataCentre.getCurrentFilmId()

    fun putCategory(it: CategoriesFilms) = DataCentre.putCategory(it)

    fun getCurrentCategory() = DataCentre.getCurrentCategory()
}