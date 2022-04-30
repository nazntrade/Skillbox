package com.example.hw_contentprovider.contacts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Long,
    val name: String,
    val phones: List<String>,
    val email: List<String>
) : Parcelable
