package ru.ktsstudio.roomdao.presentation.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ktsstudio.roomdao.data.UserRepository
import ru.ktsstudio.roomdao.data.UserRepositoryImpl
import ru.ktsstudio.roomdao.data.db.models.User
import timber.log.Timber

class UserListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        Timber.d("init $this")
    }

    private val usersMutableLiveData = MutableLiveData<List<User>>()

    val contactsLiveData: LiveData<List<User>>
        get() = usersMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                usersMutableLiveData.postValue(userRepository.getAllUsers())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                usersMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.removeUser(user.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}
