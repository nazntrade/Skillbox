package ru.zhdanon.skillcinema.data.filmstop

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.data.filmbyfilter.Country
import ru.zhdanon.skillcinema.data.filmbyfilter.Genre
import ru.zhdanon.skillcinema.entity.HomeItem

@JsonClass(generateAdapter = true)
data class FilmTop(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "nameRu") override val nameRu: String,
    @Json(name = "genres") override val genres: List<Genre>,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "countries") val countries: List<Country>,
    @Json(name = "filmLength") val filmLength: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "ratingChange") val ratingChange: Any?,
    @Json(name = "ratingVoteCount") val ratingVoteCount: Int,
    @Json(name = "year") val year: String
) : HomeItem