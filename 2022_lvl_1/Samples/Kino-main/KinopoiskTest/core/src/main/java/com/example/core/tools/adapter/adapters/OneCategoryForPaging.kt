package com.example.core.tools.adapter.adapters

import com.example.core.tools.PagedDataDelegationAdapter
import com.example.core.tools.adapter.delegate.delegateBaseFilms
import com.example.core.tools.adapter.delegate.delegateStaffForCategoryPage
import com.example.core.tools.adapter.diffutil.NestedItemDiffUtil
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.PageCategory

class OneCategoryForPaging(
    private val onClickFilm:(pageCategory: PageCategory)->Unit
) : PagedDataDelegationAdapter<NestedInfoInCategory>(NestedItemDiffUtil()) {
    init {
        delegatesManager.addDelegate(delegateBaseFilms { onClickFilm(it) })
            .addDelegate(delegateStaffForCategoryPage{ onClickFilm(it) })
    }
}