package com.example.core.tools.general

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.tools.general.GenreCountry
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "genre")
@Parcelize
class Genre(
    @ColumnInfo(name = "info")
    @SerializedName("genre") override val info: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: Int
): GenreCountry,Parcelable