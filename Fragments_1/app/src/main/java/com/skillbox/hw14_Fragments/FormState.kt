package com.skillbox.hw14_Fragments

import android.os.Parcelable
//class for safe activity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormState(
    val valid: Boolean,
    val message: String?
) : Parcelable
