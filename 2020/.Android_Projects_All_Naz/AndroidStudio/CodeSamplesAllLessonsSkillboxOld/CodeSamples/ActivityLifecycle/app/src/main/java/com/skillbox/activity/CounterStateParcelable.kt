package com.skillbox.activity

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CounterStateParcelable(
    val count: Int,
    val someString: String
): Parcelable {

    fun getIncrementedState(): CounterStateParcelable {
        return copy(count + 1, someString)
    }

    fun getDecrementedState(): CounterStateParcelable {
        return copy(count - 1, someString)
    }
}