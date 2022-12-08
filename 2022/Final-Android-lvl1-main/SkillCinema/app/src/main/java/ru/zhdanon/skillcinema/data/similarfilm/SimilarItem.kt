package ru.zhdanon.skillcinema.data.similarfilm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.data.filmbyfilter.Genre
import ru.zhdanon.skillcinema.entity.HomeItem

@JsonClass(generateAdapter = true)
data class SimilarItem(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameRu") override val nameRu: String,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "nameOriginal") val nameOriginal: String?,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "relationType") val relationType: String
) : HomeItem {
    override val rating: String? = null
    override val genres: List<Genre> = emptyList()
}