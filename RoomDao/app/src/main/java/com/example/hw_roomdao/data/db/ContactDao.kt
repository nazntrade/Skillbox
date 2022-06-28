package com.example.hw_roomdao.data.db

import androidx.room.*
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.data.db.models.ContactContract

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contact: List<Contact>)

    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    suspend fun getAllContacts(): List<Contact>

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun removeContact(contactId: Long)

//    @Query("SELECT * FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = :contactId")
//    suspend fun getContactById(contactId: Long): Contact?

}