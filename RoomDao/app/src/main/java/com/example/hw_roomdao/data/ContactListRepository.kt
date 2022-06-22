package com.example.hw_roomdao.data

import android.content.Context
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactListRepository(
    private val context: Context
) {

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        listOf(
            Contact(
                123412341234,
                "Andrew",
                "Becker",
                "asdf@ads.com",
                R.drawable.smiling_face_emoji,
                32
            ),
            Contact(
                12341277341834,
                "Andrew",
                "Wiecker",
                "asttttdf@ads.com",
                R.drawable.smiling_face_emoji,
                32
            ),
        )
    }

}