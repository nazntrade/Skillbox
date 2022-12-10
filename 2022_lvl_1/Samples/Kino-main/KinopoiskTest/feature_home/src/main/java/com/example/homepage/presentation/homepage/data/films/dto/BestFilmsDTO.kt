package com.example.homepage.presentation.homepage.data.films.dto

import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Genre

data class BestFilmsDTO(
    override val posterUrlPreview: String,
    override val rating: String? = null,
    override val nameRu: String,
    override val genres: List<Genre>,
    override val filmId: Int,
): BaseEntityFilm()