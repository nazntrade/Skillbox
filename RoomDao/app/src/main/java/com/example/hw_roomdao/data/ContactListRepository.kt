package com.example.hw_roomdao.data

import android.content.Context
import android.util.Patterns
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.Database
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactListRepository(
    private val context: Context
) {

    private val contactDao = Database.instance.contactDao()

    suspend fun saveContact(contact: Contact) {
        if(isContactValid(contact).not()) throw IncorrectFormException()
//        contactDao.insertContact(listOf(contact))
    }

    suspend fun updateContact(contact: Contact) {
        if(isContactValid(contact).not()) throw IncorrectFormException()
//        contactDao.updateContact(contact)
    }

    suspend fun removeContact(contactId: Long) {
//        contactDao.removeContactById(contactId)
    }

    suspend fun getContactById(contactId: Long): Contact? {
        return contactDao.getContactById(contactId)
    }

    suspend fun getAllContacts(): List<Contact> /*= withContext(Dispatchers.IO)*/ {
        return contactDao.getAllContacts()
    }

    private fun isContactValid(contact: Contact): Boolean {
        return contact.firstName.isNotBlank() &&
                contact.lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(contact.email).matches()
    }
}
