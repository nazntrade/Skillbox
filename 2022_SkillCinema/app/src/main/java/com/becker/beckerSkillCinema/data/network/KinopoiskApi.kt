package com.becker.beckerSkillCinema.data.network

import com.becker.beckerSkillCinema.data.filmbyfilter.ResponseByFilter
import com.becker.beckerSkillCinema.data.filmspremier.ResponsePremier
import com.becker.beckerSkillCinema.data.filmstop.ResponseTop
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT1
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT2
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT3
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskApi {

    // FragmentHome
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT2")
    @GET("v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type") type: String,
        @Query("page") page: Int
    ): ResponseTop

    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT1")
    @GET("v2.2/films/premieres")
    suspend fun getPremier(
        @Query("year") year: Int,
        @Query("month") month: String
    ): ResponsePremier

    // FragmentHome (TV_SERIES) & FragmentSearch
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT3")
    @GET("v2.2/films/")
    suspend fun getFilmsByFilter(
        @Query("countries") countries: String,
        @Query("genres") genres: Int?,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("imdbId") imdbId: String?,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): ResponseByFilter


    // FragmentFilmDetail

    // FragmentFilmDetail (series)

    // FragmentStaffDetail

}
