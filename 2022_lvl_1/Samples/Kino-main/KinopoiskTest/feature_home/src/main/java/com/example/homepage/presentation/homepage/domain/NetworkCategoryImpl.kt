package com.example.homepage.presentation.homepage.domain

import com.example.adapterdelegate.OverallApi

class NetworkCategoryImpl(
    private val filmsApi: CategoryFilmsApi,
    private val overallApi: OverallApi
): NetworkCategoryRepository
{
    override suspend fun getPremieresInNetwork(year: Int, month: String) =
        filmsApi.getPremieres(year, month).items

    override suspend fun getPopularInNetwork(page: Int) =
        filmsApi.getPopular(page).films

    override suspend fun getBestFilmsInNetwork(page: Int) = filmsApi.getBest(page).films

    override suspend fun getListGenreAndCounter() = filmsApi.getListGenreAndCountry()

    override suspend fun getFilmsGenreAndCounter(page: Int, counterID: Int, genreId: Int) =
        filmsApi.getFilmsByGenreAndCountry(page, counterID, genreId).items

    override suspend fun getSimilarFilms(id: Int) =overallApi.getSimilarFilmsByID(id)

    override  suspend fun getStaffByFilmsId(id: Int) = overallApi.getStaffFilmsByID(id)

    override suspend fun getSeries(page: Int) = filmsApi.getSeries(page)

}