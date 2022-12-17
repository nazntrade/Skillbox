package com.becker.beckerSkillCinema.data

import com.becker.beckerSkillCinema.data.filmspremier.FilmPremier
import com.becker.beckerSkillCinema.data.network.KinopoiskApi
import com.becker.beckerSkillCinema.data.network.Networking
import com.becker.beckerSkillCinema.data.network.Retrofit.retrofit
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class CinemaRepository @Inject constructor() {

    // FragmentHome
    suspend fun getFilmsTop(topType: String, page: Int): List<HomeItem> {
        return Networking.attractionsApi.getFilmsTop(type = topType, page = page).films
    }

    suspend fun getFilmsPremier(year: Int, month: String): List<FilmPremier> {
        return Networking.attractionsApi.getPremier(year, month).items
    }


    // FragmentFilmDetail


    // FragmentStaffDetail


    // FragmentSearch

}