package com.example.hw_roomdao.presentation.contact_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hw_roomdao.data.ContactListRepository
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactsListRepository = ContactListRepository(application)

    private val contactsListMutableLiveData = MutableLiveData<List<Contact>>()
    val contactListLiveData: LiveData<List<Contact>>
        get() = contactsListMutableLiveData


    fun loadList() {
        viewModelScope.launch {
            try {
                contactsListMutableLiveData.postValue(contactsListRepository.getAllContacts())
            } catch (t: Throwable) {
                contactsListMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeUser(contact: Contact) {
        viewModelScope.launch {
//            try {
//                userRepository.removeUser(user.id)
//                loadList()
//            } catch (t: Throwable) {
//                Timber.e(t)
//            }
        }
    }


}