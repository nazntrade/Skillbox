package com.example.core.tools.all

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.IDN

@Parcelize
class Query(
    var year: Int = 2022,
    var month: String = "",
    var counterID: Int = 2022,
    var genreId: Int = 2022,
    var id: Int? =null
):Parcelable