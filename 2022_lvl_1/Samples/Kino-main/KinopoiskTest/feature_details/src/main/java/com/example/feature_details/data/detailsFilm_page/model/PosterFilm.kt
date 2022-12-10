package com.example.feature_details.data.detailsFilm_page.model

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.general.Country
import com.example.core.tools.general.Genre

class PosterFilm(
    val kinopoiskId: Int,
    val countries: List<Country>,
    val genres: List<Genre>,
    val nameRu: String?,
    val nameOriginal: String?,
    val posterUrl: String,
    val ratingAgeLimits: String?,
    val year: Int,
    val filmLength: Int,
    val rating: Double,
    val shortDescription: String?,
    val description: String?,
    var isFavorite: Boolean = false,
    var isLike: Boolean = false,
    var isLook: Boolean = false,
) : NestedInfoInCategory {

    fun createInfoText(genreName: String, countriesName: String): String {
        return "$rating $nameRu\n $year, $genreName\n$countriesName, " +
                "${filmLength / 60}ч ${filmLength % 60}м ${ratingAgeLimits?.substring(3)}+"

    }
}
