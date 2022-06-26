package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.data.db.models.ContactContract

@Dao
interface ContactDao {

    suspend fun insertContact(contact: Contact)

    suspend fun updateContact(contact: Contact)

    suspend fun removeContact(contactId: Long)

    suspend fun getContactById(contactId: Long): Contact?

    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    suspend fun getAllContacts(): List<Contact>

}