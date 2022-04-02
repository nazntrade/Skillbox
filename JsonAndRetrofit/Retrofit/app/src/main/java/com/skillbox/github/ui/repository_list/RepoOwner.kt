package com.skillbox.github.ui.repository_list

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RepoOwner(
    @Json(name = "login")
    val login: String, //owner's //wrapped on owner
    @Json(name = "avatar_url")
    val avatar_url: String?  //owner's //wrapped on owner
): Parcelable
