package com.example.core.tools.adapter.adapters

import com.example.core.tools.adapter.delegate.bottomSheetDelegate

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.FilmsCollection
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CollectionAdapter(
    onClickItem: (collection: FilmsCollection, isCheck: Boolean) -> Unit
) : ListDelegationAdapter<List<NestedInfoInCategory>>() {

    init {
        delegatesManager.addDelegate(bottomSheetDelegate { collection, isCheck ->
            onClickItem(collection, isCheck)
        })

    }
}