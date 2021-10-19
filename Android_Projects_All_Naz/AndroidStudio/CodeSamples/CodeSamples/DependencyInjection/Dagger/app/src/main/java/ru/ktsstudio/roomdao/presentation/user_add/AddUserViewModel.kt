package ru.ktsstudio.roomdao.presentation.user_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ktsstudio.roomdao.R
import ru.ktsstudio.roomdao.data.IncorrectFormException
import ru.ktsstudio.roomdao.data.UserRepositoryImpl
import ru.ktsstudio.roomdao.data.db.Database
import ru.ktsstudio.roomdao.data.db.models.User
import ru.ktsstudio.roomdao.utils.SingleLiveEvent
import timber.log.Timber

class AddUserViewModel : ViewModel() {

    private val userRepository = UserRepositoryImpl(Database.instance.userDao())

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    private val existingUserMutableLiveData = MutableLiveData<User>()

    val existingUserLiveData: LiveData<User>
        get() = existingUserMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent

    fun init(id: Long) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserById(id)
                if (user != null) existingUserMutableLiveData.postValue(user)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun save(
        id: Long,
        firstName: String,
        lastName: String,
        email: String,
        avatar: String?
    ) {

        val user = User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            avatar = avatar,
            age = 20
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    userRepository.saveUser(user)
                } else {
                    userRepository.updateUser(user)
                }
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "user save error")
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.user_add_error_default
            }
        )
    }
}