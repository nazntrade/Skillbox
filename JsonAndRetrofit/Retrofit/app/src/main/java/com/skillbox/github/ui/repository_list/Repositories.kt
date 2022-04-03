package com.skillbox.github.ui.repository_list

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Repositories(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    val owner: RepoOwner
) : Parcelable
