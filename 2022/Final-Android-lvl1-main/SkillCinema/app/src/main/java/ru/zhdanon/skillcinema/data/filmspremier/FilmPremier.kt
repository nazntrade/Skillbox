package ru.zhdanon.skillcinema.data.filmspremier

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.data.filmbyfilter.Country
import ru.zhdanon.skillcinema.data.filmbyfilter.Genre
import ru.zhdanon.skillcinema.entity.HomeItem

@JsonClass(generateAdapter = true)
data class FilmPremier(
    @Json(name = "kinopoiskId") override val filmId: Int,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "nameRu") override val nameRu: String,
    @Json(name = "genres") override val genres: List<Genre>,
    @Json(name = "nameEn") val nameEn: String,
    @Json(name = "countries") val countries: List<Country>,
    @Json(name = "duration") val duration: Int?,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "premiereRu") val premiereRu: String,
    @Json(name = "year") val year: Int
) : HomeItem {
    override val rating: String? = null
}