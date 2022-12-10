package com.example.homepage.presentation.homepage.data.films.dto

import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Country
import com.example.core.tools.general.Genre
import com.google.gson.annotations.SerializedName

data class SeriesDTO(
    @SerializedName("kinopoiskId") override val filmId: Int,
    override val genres: List<Genre>,
    override val nameRu: String,
    override val posterUrlPreview: String,
    val ratingKinopoisk: Double,
    override val rating: String?  = ratingKinopoisk.toString(),
    val countries: List<Country>,
    val nameEn: String?,
    val nameOriginal: String,
    val posterUrl: String,
    val type: String,
    val year: Int,
):BaseEntityFilm()