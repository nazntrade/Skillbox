package com.example.feature_database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Genre
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "films")
@Parcelize
class FilmEntity(
    @ColumnInfo(name = "name_Ru")
    override val nameRu: String?,
    @ColumnInfo(name="poster")
    override val posterUrlPreview: String,
    @ColumnInfo(name = "genre_List")
    override val genres: List<Genre>,
    @ColumnInfo(name = "rating")
    override val rating: String?,
    @PrimaryKey
    @ColumnInfo(name ="film_id")
    override val filmId: Int,
) : BaseEntityFilm(), Parcelable