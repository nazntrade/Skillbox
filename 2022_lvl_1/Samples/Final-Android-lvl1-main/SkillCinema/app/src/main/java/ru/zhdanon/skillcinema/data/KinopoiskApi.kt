package ru.zhdanon.skillcinema.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.zhdanon.skillcinema.data.filmbyfilter.ResponseByFilter
import ru.zhdanon.skillcinema.data.filmbyfilter.ResponseGenresCountries
import ru.zhdanon.skillcinema.data.filmbyid.ResponseCurrentFilm
import ru.zhdanon.skillcinema.data.filmgallery.ResponseFilmGallery
import ru.zhdanon.skillcinema.data.filmspremier.ResponsePremier
import ru.zhdanon.skillcinema.data.filmstop.ResponseTop
import ru.zhdanon.skillcinema.data.seasons.ResponseSeasons
import ru.zhdanon.skillcinema.data.similarfilm.ResponseSimilarFilms
import ru.zhdanon.skillcinema.data.staffbyfilmid.ResponseStaffByFilmId
import ru.zhdanon.skillcinema.data.staffbyid.ResponseStaffById

interface KinopoiskApi {

    // FragmentFilmDetail
    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}")
    suspend fun getCurrentFilm(
        @Path("id") id: Int
    ): ResponseCurrentFilm

    @Headers("X-API-KEY: $API_KEY")
    @GET("v1/staff")
    suspend fun getActors(
        @Query("filmId") filmId: Int
    ): List<ResponseStaffByFilmId>

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("type") type: String = "STILL",
        @Query("page") page: Int
    ): ResponseFilmGallery

    // FragmentFilmDetail (series)
    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/seasons")
    suspend fun getSeasons(
        @Path("id") id: Int
    ): ResponseSeasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id: Int
    ): ResponseSimilarFilms

    // FragmentStaffDetail
    @Headers("X-API-KEY: $API_KEY")
    @GET("v1/staff/{id}")
    suspend fun getStaff(
        @Path("id") id: Int
    ): ResponseStaffById

    // FragmentHome
    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type") type: String,
        @Query("page") page: Int
    ): ResponseTop

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/premieres")
    suspend fun getPremier(
        @Query("year") year: Int,
        @Query("month") month: String
    ): ResponsePremier

    // FragmentHome (TV_SERIES) & FragmentSearch
    @Headers("X-API-KEY: $API_KEY")
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

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/filters")
    suspend fun getGenresCountries(): ResponseGenresCountries

    companion object {
//        private const val API_KEY = "5d6e59d8-d24e-45f8-95c9-b5b07cb478da"
//        private const val API_KEY = "b9ebd173-2eb5-4bfd-b6e9-3226369f0a43"
//        private const val API_KEY = "00ec3c68-8c85-4bd5-8508-024db2f99a4c"
        private const val API_KEY = "f746dfa5-8093-401b-8df2-e84042f3dc96"
//        private const val API_KEY = "ffcd0204-2065-4214-b6ae-aa29f5fe4003"
//        private const val API_KEY = "4f59c6e4-9f98-4f99-a7a2-e1ac2bd61d0f"  // на крайний случай
    }
}