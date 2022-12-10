package ru.zhdanon.skillcinema.data.staffbyid

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.data.filmbyfilter.Genre
import ru.zhdanon.skillcinema.entity.HomeItem

@JsonClass(generateAdapter = true)
data class StaffsFilms(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "general") val general: Boolean,
    @Json(name = "description") val description: String?,
    @Json(name = "professionKey") val professionKey: String
) : HomeItem {
    override val posterUrlPreview: String = ""
    override val genres: List<Genre> = emptyList()
}