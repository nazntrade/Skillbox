package com.example.feature_details.data.details_staff

import com.example.core.tools.base_model.films.FilmographyMove

class FilmView(
    override val description: String?,
    override val filmId: Int,
    override val general: Boolean,
    override var nameEn: String?,
    override var nameRu: String?,
    override val professionKey: String,
    override val rating: String?,
    override var posterURL: String? = null,
    override var isExpand: Boolean = false
) : FilmographyMove(description, filmId, general, nameEn, nameRu, professionKey, rating, posterURL)