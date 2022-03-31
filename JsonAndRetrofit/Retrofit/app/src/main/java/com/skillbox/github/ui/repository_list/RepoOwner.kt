package com.skillbox.github.ui.repository_list

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RepoOwner(
    val login: String?,      //owner's //wrapped on owner
    val avatar_url: String?  //owner's //wrapped on owner
): Parcelable
