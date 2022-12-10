package com.example.core.tools.base_model.category

import com.example.core.tools.base_model.category.season.Season
import com.example.core.tools.category.CategoryInfo

abstract class SeasonsCategory(
    override val category: CategoryInfo,
    open val items: List<Season>
):BaseCategory