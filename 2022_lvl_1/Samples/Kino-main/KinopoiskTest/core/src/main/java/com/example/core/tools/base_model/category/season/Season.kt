package com.example.core.tools.base_model.category.season

import android.os.Parcelable
import com.example.core.tools.all.NestedInfoInCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
class Season(
    val episodes: List<Episode>,
    val number: Int
):NestedInfoInCategory,Parcelable