package com.becker.beckerSkillCinema.data.filmbyfilter

import com.becker.beckerSkillCinema.entity.HomeItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmByFilter(
    @Json(name = "countries") val countries: List<Country>,
    @Json(name = "genres") override val genres: List<Genre>,
    @Json(name = "imdbId") val imdbId: String?,
    @Json(name = "kinopoiskId") override val filmId: Int,
    @Json(name = "nameEn") val nameEn: Any?,
    @Json(name = "nameOriginal") val nameOriginal: String?,
    @Json(name = "nameRu") val nameRus: String?,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "posterUrlPreview") val posterPreview: String,
    @Json(name = "ratingImdb") val ratingImdb: Double?,
    @Json(name = "ratingKinopoisk") val ratingKinopoisk: Double?,
    @Json(name = "type") val type: String,
    @Json(name = "year") val year: Int?
) : HomeItem {
    override val nameRu: String? = nameRus ?: nameEn?.toString() ?: nameOriginal
    override val rating: String? = ratingKinopoisk?.toString()
    override val posterUrlPreview: String = posterPreview.ifEmpty { posterUrl }
}