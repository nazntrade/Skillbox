package com.example.feature_details.data.details_staff

import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Genre

class BestFilm(
    override val nameRu: String?,
    override val posterUrlPreview: String,
    override val genres: List<Genre>,
    override val rating: String?,
    override val filmId: Int,
) : BaseEntityFilm()