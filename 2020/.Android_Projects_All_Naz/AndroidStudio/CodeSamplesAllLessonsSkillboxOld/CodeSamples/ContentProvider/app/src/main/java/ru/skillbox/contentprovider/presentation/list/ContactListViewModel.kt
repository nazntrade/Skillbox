package ru.skillbox.contentprovider.presentation.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.contentprovider.data.Contact
import ru.skillbox.contentprovider.data.ContactRepository
import ru.skillbox.contentprovider.utils.SingleLiveEvent

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository = ContactRepository(application)

    private val callMutableLiveData = SingleLiveEvent<String>()
    private val contactsMutableLiveData = MutableLiveData<List<Contact>>()

    val callLiveData: LiveData<String>
        get() = callMutableLiveData

    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                contactsMutableLiveData.postValue(contactRepository.getAllContacts())
            } catch (t: Throwable) {
                Log.e("ContactListViewModel", "contact list error", t)
                contactsMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun callToContact(contact: Contact) {
        contact.phones.firstOrNull()?.let { callMutableLiveData.postValue(it) }
    }
}