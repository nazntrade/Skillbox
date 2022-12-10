package com.example.di

import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.repository.DataBaseRepository
import com.example.feature_database.repository.FilmDataBaseRepository
import com.example.feature_details.domain.repository_ipl.FilmUseCase
import com.example.feature_details.domain.repository_ipl.GalleryUseCase
import com.example.feature_details.domain.repository_ipl.NetworkFilmDetailsRepositoryImpl
import com.example.feature_details.domain.repository_ipl.NetworkStaffRepositoryImpl
import com.example.homepage.presentation.homepage.domain.NetworkCategoryImpl
import com.example.homepage.presentation.homepage.domain.paging.PagingRepository
import com.example.screen_listpage.network.SearchPagingRepo
import com.example.screen_listpage.network.SearchRepositoryImpl
import org.koin.dsl.module


val networkRepository = module {

    single { PagingRepository(get(), get(),get()) }

    single { SearchPagingRepo(get()) }

    single { NetworkCategoryImpl(get(), get()) }

    single { NetworkFilmDetailsRepositoryImpl(get(), get()) }

    single { NetworkStaffRepositoryImpl(get()) }

    single { SearchRepositoryImpl(get()) }

}

val databaseRepository = module {
    single { DataBaseRepository(get(), get(), get()) }

    single { FilmDataBaseRepository(get(), get()) }

    single { CollectionsFilmsRepository(get(), get()) }
}

val useCase = module {

    single { FilmUseCase(get()) }

    single { GalleryUseCase(get()) }
}