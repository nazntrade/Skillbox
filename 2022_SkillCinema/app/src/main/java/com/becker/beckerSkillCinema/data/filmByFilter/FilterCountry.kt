package com.becker.beckerSkillCinema.data.filmByFilter

import com.becker.beckerSkillCinema.entity.FilterCountryGenre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterCountry(
    @Json (name = "id") override val id: Int,
    @Json (name = "country") override val name: String

) : FilterCountryGenre