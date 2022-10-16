package ru.skillbox.contentprovider.presentation.add

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.contentprovider.R
import ru.skillbox.contentprovider.data.ContactRepository
import ru.skillbox.contentprovider.data.IncorrectFormException
import ru.skillbox.contentprovider.utils.SingleLiveEvent

/**
 * @author Maxim Myalkin (MaxMyalkin) on 27.09.2020.
 */
class ContactAddViewModel(application: Application): AndroidViewModel(application) {

    private val contactRepository = ContactRepository(application)

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Int>()

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Int>
        get() = saveErrorLiveEvent

    fun save(name: String, phone: String) {
        viewModelScope.launch {
            try {
                contactRepository.saveContact(name, phone)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Log.e("ContactAddViewModel", "contact add error", t)
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when(t) {
                is IncorrectFormException -> R.string.contact_add_save_error_form
                else -> R.string.contact_add_save_error
            }
        )
    }
}