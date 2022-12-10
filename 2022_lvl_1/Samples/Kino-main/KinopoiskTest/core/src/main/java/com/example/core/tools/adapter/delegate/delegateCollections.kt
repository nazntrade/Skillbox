package com.example.core.tools.adapter.delegate

import com.example.core.databinding.ItemCollectionFilmsBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.FilmsCollection
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun bottomSheetDelegate(onClickItem: (collection: FilmsCollection, isCheck:Boolean) -> Unit) =
    adapterDelegateViewBinding<FilmsCollection, NestedInfoInCategory, ItemCollectionFilmsBinding>(
        { layoutInflater, root ->
            ItemCollectionFilmsBinding.inflate(layoutInflater, root, false)
        }) {
        binding.collection.setOnClickListener {
            item.isCheck = !item.isCheck
            onClickItem(item,item.isCheck)
        }
        bind {
            binding.collection.isChecked = item.isCheck
            binding.collection.text = item.nameCollection
            binding.listSize.text = item.size.toString()
        }
    }
