package com.example.core.tools.base_model.category

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.category.CategoryInfo

abstract class StartCategory(
    override val category: CategoryInfo,
    open val list: List<NestedInfoInCategory> = emptyList(),
    open val query: Query?=null
):BaseDetailsCategory(category,list)