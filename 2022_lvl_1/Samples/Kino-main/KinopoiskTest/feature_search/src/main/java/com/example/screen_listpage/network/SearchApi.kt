package com.example.screen_listpage.network

import com.example.core.tools.API_KEY
import com.example.core.tools.BASE_FILMS
import com.example.screen_listpage.data.ResponseSearchDTO
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {

    @Headers("X-API-KEY:$API_KEY")
    @GET(BASE_FILMS)
    suspend fun getSearchFromKeyword(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
    ): ResponseSearchDTO
}