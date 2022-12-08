package ru.zhdanon.skillcinema.data.staffbyid

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseStaffById(
    @Json(name = "personId") val personId: Int,
    @Json(name = "webUrl") val webUrl: String?,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "sex") val sex: String,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "growth") val growth: Int?,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "death") val death: String?,
    @Json(name = "age") val age: Int?,
    @Json(name = "birthplace") val birthPlace: String?,
    @Json(name = "deathplace") val deathPlace: String?,
    @Json(name = "hasAwards") val hasAwards: Int?,
    @Json(name = "profession") val profession: String?,
    @Json(name = "facts") val facts: List<String>,
    @Json(name = "spouses") val spouses: List<StaffsSpouses>?,
    @Json(name = "films") val films: List<StaffsFilms>?
)