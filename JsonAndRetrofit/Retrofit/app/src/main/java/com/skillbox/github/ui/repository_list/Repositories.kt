package com.skillbox.github.ui.repository_list

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.security.acl.Owner

@Parcelize
@JsonClass(generateAdapter = true)
data class Repositories(
    val id: Long?,
    val name: String?,
    val owner: RepoOwner?
) : Parcelable
