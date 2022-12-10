package com.example.feature_details.data.season

import com.example.core.tools.base_model.category.SeasonsCategory
import com.example.core.tools.base_model.category.season.Season
import com.example.core.tools.category.CategoryInfo

class SeasonsItem(
    override val category: CategoryInfo,
    override val items: List<Season>
):SeasonsCategory(category, items)