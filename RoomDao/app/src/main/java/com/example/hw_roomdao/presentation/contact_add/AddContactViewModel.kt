package com.example.hw_roomdao.presentation.contact_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.ContactListRepository
import com.example.hw_roomdao.data.IncorrectFormException
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class AddContactViewModel : ViewModel() {

    private val contactRepository = ContactListRepository()

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    private val existingContactMutableLiveData = MutableLiveData<Contact>()

    val existingContactLiveData: LiveData<Contact?>
        get() = existingContactMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent

    fun init(id: Long) {
        viewModelScope.launch {
            try {
                val user = contactRepository.getContactById(id)
                existingContactMutableLiveData.postValue(user)
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

        val contact = Contact(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            avatar = avatar
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    contactRepository.saveContact(contact)
                } else {
                    contactRepository.updateContact(contact)
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