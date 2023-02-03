package com.becker.beckerSkillCinema.data.network

import com.becker.beckerSkillCinema.data.filmByFilter.ResponseByFilter
import com.becker.beckerSkillCinema.data.filmByFilter.ResponseGenresCountries
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.filmGallery.ResponseFilmGallery
import com.becker.beckerSkillCinema.data.filmsPremier.ResponsePremier
import com.becker.beckerSkillCinema.data.filmsTop.ResponseTop
import com.becker.beckerSkillCinema.data.seasons.ResponseSeasons
import com.becker.beckerSkillCinema.data.similarFilm.ResponseSimilarFilms
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.data.staffById.ResponseStaffById
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT1
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT2
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT3
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT4
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT5
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT6
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT7
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT8
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT9
import com.becker.beckerSkillCinema.utils.Constants.KINOPOISKACCESSRIGHT10
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
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
        @Query("genres") genres: String,
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
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT4")
    @GET("v2.2/films/{id}")
    suspend fun getCurrentFilm(
        @Path("id") id: Int
    ): ResponseCurrentFilm

    // StaffInFilm
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT5")////////////////////////////
    @GET("v1/staff")
    suspend fun getActors(
        @Query("filmId") filmId: Int
    ): List<ResponseStaffByFilmId>

    // FragmentStaffDetail
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT6")////////////////////////
    @GET("v1/staff/{id}")
    suspend fun getStaff(
        @Path("id") id: Int
    ): ResponseStaffById

    // SimilarFilms
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT7")
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id: Int
    ): ResponseSimilarFilms

    // FragmentFilmDetail (series)
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT8")
    @GET("v2.2/films/{id}/seasons")
    suspend fun getSeasons(
        @Path("id") id: Int
    ): ResponseSeasons

    // Images
    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT9") ///////////////////////////////////////
    @GET("v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("type") type: String = "STILL",
        @Query("page") page: Int
    ): ResponseFilmGallery

    @Headers("X-API-KEY: $KINOPOISKACCESSRIGHT10")
    @GET("v2.2/films/filters")
    suspend fun getGenresCountries(): ResponseGenresCountries


}
