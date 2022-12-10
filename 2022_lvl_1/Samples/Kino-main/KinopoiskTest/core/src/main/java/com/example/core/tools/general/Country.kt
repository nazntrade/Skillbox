package com.example.core.tools.general

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.tools.general.GenreCountry
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country")
class Country(
    @ColumnInfo(name = "info")
    @SerializedName("country") override val info: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: Int
) : GenreCountry