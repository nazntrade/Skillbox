package com.example.homepage.presentation.homepage.tools

import com.example.core.tools.extensions.toBaseFilmList
import com.example.homepage.presentation.homepage.domain.NetworkCategoryRepository

suspend fun loadPremieres(
    networkRepository: NetworkCategoryRepository,
    year: Int, month: String
) = networkRepository.getPremieresInNetwork(year, month).toBaseFilmList()

suspend fun loadSerials(
    networkRepository: NetworkCategoryRepository,
    page: Int
) = networkRepository.getSeries(page).items.toBaseFilmList()

suspend fun loadBest(
    networkRepository: NetworkCategoryRepository,
    page: Int
) = networkRepository.getBestFilmsInNetwork(page).toBaseFilmList()

suspend fun loadPopular(
    networkRepository: NetworkCategoryRepository,
    page: Int
) = networkRepository.getPopularInNetwork(page).toBaseFilmList()


suspend fun loadFilmsByCounterAdnGenre(
    networkRepository: NetworkCategoryRepository,
    page: Int,
    counterId: Int,
    genreId: Int
) = networkRepository.getFilmsGenreAndCounter(page, counterId, genreId).toBaseFilmList()

