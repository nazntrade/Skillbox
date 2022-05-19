package com.example.hw_contentprovider.contacts.new_contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.contacts.data.ContactsRepository
import com.example.hw_contentprovider.contacts.data.IncorrectFormException
import com.example.hw_contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactsRepository = ContactsRepository(application)

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    val saveSuccessViewModel: LiveData<Unit>
        get() = saveSuccessLiveEvent

    private val saveErrorLiveEvent = SingleLiveEvent<Int>()
    val saveErrorViewModel: LiveData<Int>
        get() = saveErrorLiveEvent

    fun save(firstName: String, secondName: String, phone: String, email: String) {
        viewModelScope.launch {
            try {
                contactsRepository.saveContact(firstName, secondName, phone, email)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.the_form_was_filled_out_incorrectly
                else -> R.string.contact_add_save_error
            }
        )
    }

}