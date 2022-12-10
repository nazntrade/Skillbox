package com.example.homepage.presentation.homepage.domain


import com.example.core.tools.*
import com.example.homepage.presentation.homepage.data.films.dto.CountryAndGenreDTO
import com.example.homepage.presentation.homepage.data.response.*

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CategoryFilmsApi {
    @Headers("X-API-KEY:$API_KEY")
    @GET(PREMIERES)
    suspend fun getPremieres(
        @Query("year") year: Int, @Query("month") month: String
    ): ResponsePremieres

    @Headers("X-API-KEY:$API_KEY")
    @GET(POPULAR)
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("type") type: String = TOP_100_POPULAR
    ): ResponsePopular

    @Headers("X-API-KEY:$API_KEY")
    @GET(POPULAR)
    suspend fun getBest(
        @Query("page") page: Int,
        @Query("type") type: String = TOP_250_BEST
    ): ResponseFilms

    @Headers("X-API-KEY:$API_KEY")
    @GET(GET_FILTERS)
    suspend fun getListGenreAndCountry(): CountryAndGenreDTO

    @Headers("X-API-KEY:$API_KEY")
    @GET(BASE_FILMS)
    suspend fun getFilmsByGenreAndCountry(
        @Query("page") page: Int = 1,
        @Query("countries") countries: Int = 3,
        @Query("genres") genres: Int = 2,
    ): ResponseFilterFilms

    @Headers("X-API-KEY:$API_KEY")
    @GET("api/v2.2/films")
    suspend fun getSeries(
        @Query("page") page: Int,
        @Query("type") type:String ="TV_SERIES",
    ): ResponseSeries

}