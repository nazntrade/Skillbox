package com.example.hw_contentprovider.contacts.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsListRepository(
    private val context: Context
) {

    fun getAllContacts(): List<Contact> =
}