package com.becker.beckerSkillCinema.data

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


    // FragmentFilmDetail


    // FragmentStaffDetail


    // FragmentSearch

}