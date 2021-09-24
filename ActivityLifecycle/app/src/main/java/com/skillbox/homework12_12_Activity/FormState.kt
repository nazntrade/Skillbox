package com.skillbox.homework12_12_Activity

import android.os.Parcelable
<<<<<<< HEAD
import kotlinx.parcelize.Parcelize
//class for safe activity
=======
import kotlinx.android.parcel.Parcelize

>>>>>>> 3d19bbaac05370902f9c22ca398b7381589777a1
@Parcelize
data class FormState(
    val valid: Boolean,
    val message: String?
) : Parcelable
