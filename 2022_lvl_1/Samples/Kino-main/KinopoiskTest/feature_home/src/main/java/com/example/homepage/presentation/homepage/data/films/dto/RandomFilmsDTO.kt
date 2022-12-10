package com.example.homepage.presentation.homepage.data.films.dto

import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Genre
import com.google.gson.annotations.SerializedName

data class RandomFilmsDTO(
    @SerializedName("kinopoiskId") override val filmId: Int,
    override val nameRu: String,
    override val genres: List<Genre>,
    override val posterUrlPreview: String,
    private val ratingKinopoisk: Double,
    override val rating: String = ratingKinopoisk.toString()
) : BaseEntityFilm()