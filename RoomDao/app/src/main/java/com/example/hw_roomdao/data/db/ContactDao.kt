package com.example.hw_roomdao.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.data.db.models.ContactContract

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contact: List<Contact>)

    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    fun getAllContacts(): List<Contact>

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun removeContact(contact: Contact)

    @Query("DELETE FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = :userId")
    fun removeContactById(userId: Long)


    @Query("SELECT * FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = :contactId")
    fun getContactById(contactId: Long): Contact

}