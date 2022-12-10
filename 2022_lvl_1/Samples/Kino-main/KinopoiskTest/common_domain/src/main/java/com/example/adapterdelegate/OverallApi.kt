package com.example.adapterdelegate

import com.example.adapterdelegate.data.ResponseSimilar
import com.example.adapterdelegate.data.StaffFromFilmsDTO
import com.example.core.tools.API_KEY
import com.example.core.tools.STAFF
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface OverallApi {


    @Headers("X-API-KEY:$API_KEY")
    @GET(STAFF)
    suspend fun getStaffFilmsByID(@Query("filmId") id: Int): List<StaffFromFilmsDTO>

    @Headers("X-API-KEY:$API_KEY")
    @GET("api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilmsByID(
        @Path("id") id: Int,
    ): ResponseSimilar
}