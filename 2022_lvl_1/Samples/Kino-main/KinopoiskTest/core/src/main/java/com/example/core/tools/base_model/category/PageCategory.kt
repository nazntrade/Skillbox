package com.example.core.tools.base_model.category

import android.os.Parcelable
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.category.CategoryInfo
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class PageCategory(
    override val category: CategoryInfo,
    override val list: @RawValue List<NestedInfoInCategory>,
    override  val query: Query?=null
) : StartCategory(category, list), Parcelable