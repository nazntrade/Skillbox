package com.example.core.tools.base_model.films

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.general.Genre

interface SearchFilms : NestedInfoInCategory {
    val filmId: Int
    val rating: String
    val nameRu: String?
    val genres: List<Genre>
    val posterUrlPreview: String
    val nameEn: String?
    val nameOriginal: String
    val year: Int
}