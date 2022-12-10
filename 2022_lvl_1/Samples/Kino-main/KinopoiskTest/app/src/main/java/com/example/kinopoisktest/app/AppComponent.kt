package com.example.kinopoisktest.app

import android.app.Application
import com.example.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppComponent : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppComponent)
            modules(
                listOf(
                    filmsApi,
                    dataBaseModule,
                    viewModel,
                    networkRepository,
                    databaseRepository,
                    useCase
                )
            )
        }
    }
}