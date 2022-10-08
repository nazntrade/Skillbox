package com.skillbox.homework13_opening_new_screens

import android.os.Parcelable
//class for safe activity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormState(
    val valid: Boolean,
    val message: String?
) : Parcelable
