package com.example.hw_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.data.db.models.ContactContract

@Dao
interface ContactDao {

     fun insertContact(contact: Contact)

     fun updateContact(contact: Contact)

     fun removeContact(contactId: Long)

     fun getContactById(contactId: Long): Contact?

    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    fun getAllContacts(): List<Contact>

}