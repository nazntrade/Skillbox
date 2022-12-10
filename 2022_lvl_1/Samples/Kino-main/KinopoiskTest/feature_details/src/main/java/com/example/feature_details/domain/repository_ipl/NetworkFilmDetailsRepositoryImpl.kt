package com.example.feature_details.domain.repository_ipl

import com.example.adapterdelegate.OverallApi
import com.example.feature_details.domain.DetailFilmsApi
import com.example.feature_details.domain.NetworkFilmsDetailsRepository

class NetworkFilmDetailsRepositoryImpl(
   private val filmsApi: DetailFilmsApi,
   private val overallApi:OverallApi
): NetworkFilmsDetailsRepository {

    override suspend fun getFilmForID(id:Int) = filmsApi.getFilmsByID(id)

    override suspend fun getAllStaffFilmsByID(filmsID:Int) = overallApi.getStaffFilmsByID(filmsID)

    override suspend fun getGalleryFilmsByID(filmsID:Int,type:String,page:Int) = filmsApi.getGalleryFilmsByID(filmsID,type,page)

    override suspend fun getSimilarFilmsByID(filmsID:Int) = overallApi.getSimilarFilmsByID(filmsID).items

    override suspend fun getSeasons(id: Int) = filmsApi.getSerials(id)

}