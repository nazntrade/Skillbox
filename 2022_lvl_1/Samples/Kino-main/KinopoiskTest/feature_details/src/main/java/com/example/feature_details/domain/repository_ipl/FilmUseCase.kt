package com.example.feature_details.domain.repository_ipl

class FilmUseCase(private val networkDetailsRepositoryImpl: NetworkFilmDetailsRepositoryImpl) {

    suspend fun getFilmByID(id:Int) = networkDetailsRepositoryImpl.getFilmForID(id)

}