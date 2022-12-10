package ru.zhdanon.skillcinema.data

data class ParamsFilterFilm(
    val countries: Map<Int, String> = emptyMap(),
    val genres: Map<Int, String> = emptyMap(),
    val order: String = "RATING",
    val type: String = "",
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val yearFrom: Int = 1000,
    val yearTo: Int = 3000,
    val imdbId: String? = null,
    val keyword: String = ""
)

data class ParamsFilterGallery(
    val filmId: Int = 328,
    val galleryType: String = "STILL"
)