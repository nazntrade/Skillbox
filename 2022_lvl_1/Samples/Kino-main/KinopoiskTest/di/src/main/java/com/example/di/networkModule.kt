package com.example.di

import androidx.lifecycle.SavedStateHandle
import com.example.adapterdelegate.OverallApi
import com.example.core.tools.BASE_URL
import com.example.feature_details.domain.DetailFilmsApi
import com.example.homepage.presentation.homepage.domain.CategoryFilmsApi
import com.example.screen_listpage.network.SearchApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val filmsApi = module {

    single(named("retrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CategoryFilmsApi> {
        get<Retrofit>(named("retrofit")).create()
    }

    single<DetailFilmsApi> {
        get<Retrofit>(named("retrofit")).create()
    }

    single<OverallApi> {
        get<Retrofit>(named("retrofit")).create()
    }

    single<SearchApi> {
        get<Retrofit>(named("retrofit")).create()
    }

    single {
        SavedStateHandle()
    }

}
