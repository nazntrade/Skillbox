package ru.ktsstudio.roomdao.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.ktsstudio.roomdao.data.UserRepository
import ru.ktsstudio.roomdao.data.UserRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesUserRepository(impl: UserRepositoryImpl): UserRepository
}