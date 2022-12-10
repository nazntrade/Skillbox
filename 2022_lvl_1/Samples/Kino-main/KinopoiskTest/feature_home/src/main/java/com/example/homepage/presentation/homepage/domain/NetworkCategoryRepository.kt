package com.example.homepage.presentation.homepage.domain

import com.example.adapterdelegate.data.ResponseSimilar
import com.example.adapterdelegate.data.StaffFromFilmsDTO
import com.example.homepage.presentation.homepage.data.films.dto.*
import com.example.homepage.presentation.homepage.data.response.ResponseSeries

interface NetworkCategoryRepository {

    suspend fun getPremieresInNetwork(year: Int, month: String): List<PremieresDTO>

    suspend fun getPopularInNetwork(page: Int): List<PopularDTO>

    suspend fun  getBestFilmsInNetwork(page: Int): List<BestFilmsDTO>

    suspend fun  getListGenreAndCounter(): CountryAndGenreDTO

    suspend fun  getFilmsGenreAndCounter(page: Int,counterID:Int,genreId:Int ): List<RandomFilmsDTO>

    suspend fun getSimilarFilms(id:Int): ResponseSimilar

    suspend fun  getStaffByFilmsId(id: Int): List<StaffFromFilmsDTO>

    suspend fun getSeries(page:Int):ResponseSeries

}
