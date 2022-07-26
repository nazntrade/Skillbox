package com.example.hw_roomdao.data

import android.util.Patterns
import androidx.lifecycle.LiveData
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactListRepository {

    private val contactDao = Database.instance.contactDao()

    suspend fun saveContact(contact: Contact) {
        if (isContactValid(contact).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            contactDao.insertContacts(listOf(contact))
        }
    }

    suspend fun updateContact(contact: Contact) {
        if (isContactValid(contact).not()) throw IncorrectFormException()
        return withContext(Dispatchers.IO) {
            contactDao.updateContact(contact)
        }
    }

    suspend fun removeContact(contactId: Long) {
        return withContext(Dispatchers.IO) {
            //contactDao.removeContactById(contactId)
        }
    }

    suspend fun getContactById(contactId: Long): Contact {
        return withContext(Dispatchers.IO) {
            contactDao.getContactById(contactId)
        }
    }

    suspend fun getAllContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            contactDao.getAllContacts()
        }
    }

    private fun isContactValid(contact: Contact): Boolean {
        return contact.firstName.isNotBlank() &&
                contact.lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(contact.email).matches()
    }

    suspend fun createChatWithSelectedContact(selectedContact: Contact) {

    }
}
