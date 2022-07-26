package com.example.hw_roomdao.presentation.contact_list

import androidx.lifecycle.*
import com.example.hw_roomdao.data.ContactsRepository
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactListViewModel() : ViewModel() {

    private val contactsListRepository = ContactsRepository()

    private val contactsListMutableLiveData = MutableLiveData<List<Contact>>()
    val contactListLiveData: LiveData<List<Contact>>
        get() = contactsListMutableLiveData


    fun loadList() {
        viewModelScope.launch {
            try {
                contactsListMutableLiveData.postValue(contactsListRepository.getAllContacts())
            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun addContactToChatList(selectedContact: Contact) {
        viewModelScope.launch {
            try {

            } catch (t: Throwable) {
                Timber.e(t, "user list error")
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeUser(contact: Contact) {
        viewModelScope.launch {
            try {
                contactsListRepository.removeContact(contact.id)
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}