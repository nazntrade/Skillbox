package com.example.feature_details.domain.repository_ipl

class GalleryUseCase(private val networkRepository: NetworkFilmDetailsRepositoryImpl) {

   suspend fun getGallery(id:Int,type:String,page:Int) = networkRepository.getGalleryFilmsByID(id,type,page)

}