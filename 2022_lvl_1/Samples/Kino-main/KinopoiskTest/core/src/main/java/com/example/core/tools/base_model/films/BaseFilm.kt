package com.example.core.tools.base_model.films

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.general.Genre

class BaseFilm(
    val nameRu: String? ="",
    val posterUrlPreview: String="",
    val genres: List<Genre> = emptyList(),
    val rating: String?=null,
    val filmId: Int = -1,
    var isFavorite: Boolean = false,
    var isLook: Boolean = false,
    var isLike: Boolean = false
) : NestedInfoInCategory