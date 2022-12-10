package com.example.core.tools.base_model.category.season

import android.os.Parcelable
import com.example.core.tools.all.NestedInfoInCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
class Episode(
    val episodeNumber: Int,
    val nameEn: String,
    val nameRu: String,
    val releaseDate: String,
    val seasonNumber: Int,
    val synopsis: String
):NestedInfoInCategory,Parcelable