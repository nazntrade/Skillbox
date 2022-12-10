package com.example.core.tools.adapter.delegate

import androidx.core.view.isVisible
import com.example.core.databinding.ItemCollectionBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.NestedCollection
import com.example.core.tools.extensions.glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun collectionsDelegate(onClickItem:(pageCategory: PageCategory)->Unit) =
    adapterDelegateViewBinding<NestedCollection, NestedInfoInCategory, ItemCollectionBinding>({ layoutInflater, root ->
        ItemCollectionBinding.inflate(layoutInflater, root, false)
    }) {

        binding.root.setOnClickListener{
            onClickItem(PageCategory(CategoryInfo.COLLECTION.apply { text = item.nameCollection }, emptyList(), Query(id = item.id)))
        }

        bind {
            binding.collectionName.text = item.nameCollection
            binding.amountFilms.text = item.size.toString()
            binding.imageCollection.rotation =
                if (item.icon == com.example.core.R.drawable.ic_close) 45f
                else 0f
            binding.imageCollection.glide(item.icon)
            binding.deleteButton.isVisible =item.isDelete
        }
    }