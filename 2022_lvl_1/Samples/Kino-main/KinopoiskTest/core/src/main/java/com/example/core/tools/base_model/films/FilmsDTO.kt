package com.example.core.tools.base_model.films

import com.example.core.tools.general.Genre

interface FilmsDTO {
    val nameRu: String?
    val posterUrlPreview: String
    val genres: List<Genre>
    val rating: String?
    val filmId: Int
}