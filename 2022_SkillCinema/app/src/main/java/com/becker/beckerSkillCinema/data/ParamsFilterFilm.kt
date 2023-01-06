package com.becker.beckerSkillCinema.data

data class ParamsFilterFilm(
    val countries: Map<Int, String> = emptyMap(),
    val genres: Int? = null,
    val order: String = "RATING",
    val type: String = "",
    val ratingFrom: Int = 4,
    val ratingTo: Int = 10,
    val yearFrom: Int = 1800,
    val yearTo: Int = 3000,
    val imdbId: String? = null,
    val keyword: String = ""
)

data class ParamsFilterGallery(
    val filmId: Int = 328,
    val galleryType: String = "STILL"
)
