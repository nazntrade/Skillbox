package com.example.clientContprovider.contacts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id: Long,
    val title: String,
) : Parcelable
