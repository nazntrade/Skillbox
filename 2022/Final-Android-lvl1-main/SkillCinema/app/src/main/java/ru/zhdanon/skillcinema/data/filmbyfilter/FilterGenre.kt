package ru.zhdanon.skillcinema.data.filmbyfilter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.entity.FilterCountryGenre

@JsonClass(generateAdapter = true)
data class FilterGenre(
    @Json(name = "id") override val id: Int,
    @Json(name = "genre") override val name: String
) : FilterCountryGenre
