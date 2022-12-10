package com.example.core.tools.base_model.films

abstract class BaseEntityFilm(
    var isFavorite: Boolean = false,
    var isLook: Boolean = false,
    var isLike: Boolean = false
) : FilmsDTO {
    fun toBaseFilm()
    = BaseFilm(nameRu, posterUrlPreview, genres, rating, filmId, isFavorite, isLook, isLike)
}