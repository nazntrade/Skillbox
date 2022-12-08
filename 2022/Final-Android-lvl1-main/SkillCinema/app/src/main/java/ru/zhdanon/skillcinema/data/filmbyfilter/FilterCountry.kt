package ru.zhdanon.skillcinema.data.filmbyfilter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zhdanon.skillcinema.entity.FilterCountryGenre

@JsonClass(generateAdapter = true)
data class FilterCountry(
    @Json (name = "id") override val id: Int,
    @Json (name = "country") override val name: String

) : FilterCountryGenre