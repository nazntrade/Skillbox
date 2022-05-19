package com.example.hw_contentprovider.contacts.detailInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hw_contentprovider.contacts.data.ContactsRepository
import com.example.hw_contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailContactInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactsRepository(application)

    private val deleteSuccessLiveEvent = SingleLiveEvent<Unit>()
    val deleteSuccessLiveData: LiveData<Unit>
        get() = deleteSuccessLiveEvent

    fun deleteContactViewModel(args: DetailContactInfoFragmentArgs) {
        viewModelScope.launch {
            try {
                repository.deleteContact(args)
                deleteSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
            }
        }
    }
}