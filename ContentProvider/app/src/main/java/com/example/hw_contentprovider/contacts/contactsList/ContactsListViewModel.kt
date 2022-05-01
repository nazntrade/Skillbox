package com.example.hw_contentprovider.contacts.contactsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hw_contentprovider.contacts.data.Contact
import com.example.hw_contentprovider.contacts.data.ContactsRepository
import kotlinx.coroutines.launch

class ContactsListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactsListRepository = ContactsRepository(application)

    private val contactsListMutableLiveData = MutableLiveData<List<Contact>>()
    val contactListLiveData: LiveData<List<Contact>>
        get() = contactsListMutableLiveData


    fun loadList(){
        viewModelScope.launch {
            try {
                contactsListMutableLiveData.postValue(contactsListRepository.getAllContacts())
            }catch (t: Throwable) {
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }
}