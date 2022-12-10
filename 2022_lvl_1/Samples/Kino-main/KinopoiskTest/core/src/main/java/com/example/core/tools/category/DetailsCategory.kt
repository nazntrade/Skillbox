package com.example.core.tools.category

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.BaseDetailsCategory

class DetailsCategory(
    override val category: CategoryInfo,
    override val listValue: List<NestedInfoInCategory>,
) : BaseDetailsCategory(category, listValue)