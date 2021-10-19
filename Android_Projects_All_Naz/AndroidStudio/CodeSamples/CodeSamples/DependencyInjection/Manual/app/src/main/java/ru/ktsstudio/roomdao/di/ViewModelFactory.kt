package ru.ktsstudio.roomdao.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ktsstudio.roomdao.presentation.user_list.UserListViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            UserListViewModel::class.java -> DiContainer.getUserListViewModel() as T
            else -> error("Factory cannot create viewmodel $modelClass")
        }
    }
}