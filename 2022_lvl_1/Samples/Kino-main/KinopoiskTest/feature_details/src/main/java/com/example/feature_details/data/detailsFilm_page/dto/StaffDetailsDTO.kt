package com.example.feature_details.data.detailsFilm_page.dto

import com.example.feature_details.data.details_staff.Film

class StaffDetailsDTO(
    val films: ArrayList<Film>,
    val nameRu: String,
    val posterUrl: String,
    val profession: String,
)