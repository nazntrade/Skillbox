package com.example.core.tools.adapter.adapters

import com.example.core.tools.adapter.delegate.delegateBasCategory
import com.example.core.tools.adapter.delegate.delegateDescription
import com.example.core.tools.adapter.delegate.delegateSeasons
import com.example.core.tools.adapter.diffutil.CategoryDiffUtil
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CategoryAdapterBase(
    private val onClickItem: (category: PageCategory) -> Unit?,
    private val onClickAll: (category: BaseCategory) -> Unit,
) : AsyncListDifferDelegationAdapter<BaseCategory>(CategoryDiffUtil()) {

    init {
        delegatesManager.addDelegate(delegateBasCategory({ onClickItem(it) }, { onClickAll(it) }))
            .addDelegate(delegateDescription())
            .addDelegate(delegateSeasons { onClickItem(it) })
    }

}