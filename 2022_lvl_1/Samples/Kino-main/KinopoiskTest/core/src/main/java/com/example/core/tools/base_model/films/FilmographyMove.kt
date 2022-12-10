package com.example.core.tools.base_model.films

import com.example.core.tools.all.NestedInfoInCategory

abstract class FilmographyMove(
    open val description: String?,
    open val filmId: Int,
    open val general: Boolean,
    open var nameEn: String?,
    open var nameRu: String?,
    open val professionKey: String,
    open val rating: String?,
    open var posterURL: String? = null,
    open var isExpand: Boolean = false
) : NestedInfoInCategory