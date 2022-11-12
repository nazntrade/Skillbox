package ru.ktsstudio.roomdao.di

import ru.ktsstudio.roomdao.data.UserRepository
import ru.ktsstudio.roomdao.data.UserRepositoryImpl
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.presentation.user_list.UserListViewModel

object DiContainer {

    private fun getUserRepository(): UserRepository {
        return UserRepositoryImpl(Database.instance.userDao())
    }

    fun getUserListViewModel(): UserListViewModel {
        val repository = getUserRepository()
        return UserListViewModel(repository)
    }
}