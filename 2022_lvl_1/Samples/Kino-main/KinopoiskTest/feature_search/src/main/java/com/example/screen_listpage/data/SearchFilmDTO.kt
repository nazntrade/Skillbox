package com.example.screen_listpage.data

import com.example.core.tools.base_model.films.SearchFilms
import com.example.core.tools.general.Genre

import com.google.gson.annotations.SerializedName

class SearchFilmDTO(
    @SerializedName("kinopoiskId") override val filmId: Int,
    val ratingKinopoisk: Double,
    override val nameRu: String,
    override val genres: List<Genre>,
    override val posterUrlPreview: String,
    override val nameEn: String?,
    override val nameOriginal: String,
    override val year: Int,
    override val rating: String = ratingKinopoisk.toString(),
) : SearchFilms