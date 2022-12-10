package com.example.feature_details.data.detailsFilm_page.model

import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.DescriptionsCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.general.InfoFilms

class DescriptionFilms(
    override val category: CategoryInfo,
    override val shortDescription: String?,
    override val description: String?,
): DescriptionsCategory(category, shortDescription, description)