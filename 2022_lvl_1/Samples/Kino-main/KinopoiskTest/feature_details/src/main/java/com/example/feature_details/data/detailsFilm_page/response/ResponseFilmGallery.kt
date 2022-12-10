package com.example.feature_details.data.detailsFilm_page.response

import com.example.feature_details.data.detailsFilm_page.dto.GalleryDTO

data class ResponseFilmGallery(
    val items: List<GalleryDTO>,
    val total:Int,
    val totalPages:Int,
)