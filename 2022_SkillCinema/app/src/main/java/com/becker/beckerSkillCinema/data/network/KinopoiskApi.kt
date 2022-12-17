package com.becker.beckerSkillCinema.data.network

import com.becker.beckerSkillCinema.data.filmspremier.ResponsePremier
import com.becker.beckerSkillCinema.data.filmstop.ResponseTop
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT1
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT2
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskApi {

    // FragmentFilmDetail

    // FragmentFilmDetail (series)

    // FragmentStaffDetail

    // FragmentHome
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT2")
    @GET("v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type")
        type: String,
        @Query("page")
        page: Int
    ): ResponseTop

    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT1")
    @GET("v2.2/films/premieres")
    suspend fun getPremier(
        @Query("year")
        year: Int,
        @Query("month")
        month: String
    ): ResponsePremier


    // FragmentHome (TV_SERIES) & FragmentSearch

}
