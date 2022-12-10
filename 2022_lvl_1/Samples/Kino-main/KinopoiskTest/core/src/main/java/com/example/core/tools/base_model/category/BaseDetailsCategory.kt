package com.example.core.tools.base_model.category

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.category.CategoryInfo

abstract class BaseDetailsCategory(
    override val category: CategoryInfo,
    open val listValue: List<NestedInfoInCategory>
) : BaseCategory