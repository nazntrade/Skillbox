package com.skillbox.homework12_12_Activity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//class for safe activity
@Parcelize
data class FormState(
    val valid: Boolean,
    val message: String?
) : Parcelable
