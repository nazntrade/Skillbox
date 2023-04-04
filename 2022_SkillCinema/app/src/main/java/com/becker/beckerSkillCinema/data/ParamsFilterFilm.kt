package com.becker.beckerSkillCinema.data

import com.becker.beckerSkillCinema.presentation.search.SearchSettingsFragment
import java.util.*

data class ParamsFilterFilm(
    val countries: Map<Int, String> = emptyMap(),
    val genres: Map<Int, String> = emptyMap(),
    val order: String = SearchSettingsFragment.Companion.Order.RATING.text,
    val type: String = SearchSettingsFragment.Companion.Type.ALL.text,
    val ratingFrom: Int = 1,
    val ratingTo: Int = 10,
    val yearFrom: Int = 1850,
    val yearTo: Int = Calendar.getInstance().get(Calendar.YEAR),
    val imdbId: String? = null,
    val keyword: String = ""
)

data class ParamsFilterGallery(
    val filmId: Int = 328,
    val galleryType: String = "STILL"
)