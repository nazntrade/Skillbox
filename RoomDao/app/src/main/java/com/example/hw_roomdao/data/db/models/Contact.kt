package com.example.hw_roomdao.data.db.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: Int,
    val age: Int

) : Parcelable