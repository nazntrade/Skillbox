package com.example.hw_roomdao.data

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactListRepository(
    private val context: Context
) {

    suspend fun getAllContacts(): List<User> = withContext(Dispatchers.IO) {
        listOf(
            User(
                123412341234,
                "Andrew",
                "Becker",
                "asdf@ads.com",
                "${R.drawable.smiling_face_emoji}",
                32
            ),
            User(
                12341277341834,
                "Andrew",
                "Wiecker",
                "asttttdf@ads.com",
                "${R.drawable.smiling_face_emoji}",
                32
            ),
        )
    }

}