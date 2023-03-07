package com.becker.beckerSkillCinema.data.repositories

import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.DataCentre
import com.becker.beckerSkillCinema.data.ParamsFilterFilm
import com.becker.beckerSkillCinema.data.filmByFilter.ResponseByFilter
import com.becker.beckerSkillCinema.data.filmByFilter.ResponseGenresCountries
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
            countries = if (filters.countries.isNotEmpty()) filters.countries.keys.first()
                .toString() else "",
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

    suspend fun getGenresCountries(): ResponseGenresCountries {
        return Networking.kinopoiskApi.getGenresCountries()
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

    suspend fun getPeopleFromSearch(name: String, page: Int) =
        Networking.kinopoiskApi.getPeopleFromSearch(name, page)

    fun putFilmId(filmId: Int) = DataCentre.putCurrentFilmId(filmId)

    fun getCurrentFilmId() = DataCentre.getCurrentFilmId()

    fun putCategory(it: CategoriesFilms) = DataCentre.putCategory(it)

    fun getCurrentCategory() = DataCentre.getCurrentCategory()
}