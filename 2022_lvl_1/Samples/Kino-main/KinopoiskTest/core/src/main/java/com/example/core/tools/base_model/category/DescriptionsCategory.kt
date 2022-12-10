package com.example.core.tools.base_model.category

import com.example.core.tools.category.CategoryInfo

abstract class DescriptionsCategory(
    override val category: CategoryInfo,
    open val shortDescription: String?,
    open val description: String?,
):BaseCategory