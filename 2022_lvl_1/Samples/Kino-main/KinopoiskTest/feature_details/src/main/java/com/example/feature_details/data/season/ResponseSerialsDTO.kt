package com.example.feature_details.data.season

import com.example.core.tools.base_model.category.season.Season

data class ResponseSerialsDTO(
    val items: List<Season>,
    val total: Int
)