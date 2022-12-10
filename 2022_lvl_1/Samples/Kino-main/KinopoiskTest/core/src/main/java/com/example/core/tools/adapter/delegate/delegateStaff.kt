package com.example.core.tools.adapter.delegate

import com.example.core.databinding.ItemFilmsBinding
import com.example.core.databinding.ItemStaffBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.Staff
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateStaff(onClickItem: (pageCategory: PageCategory) -> Unit) = adapterDelegateViewBinding<Staff, NestedInfoInCategory, ItemStaffBinding>({ layoutInflater, root ->
    ItemStaffBinding.inflate(layoutInflater, root, false)
}) {

    binding.poster.setOnClickListener {
        onClickItem(PageCategory(CategoryInfo.STAFF, emptyList(),Query(id = item.staffId)))
    }

    bind{
        binding.poster.glide(item.posterUrl)
        binding.description.text = item.description
        binding.name.text = item.nameRu
    }
}

fun delegateStaffForCategoryPage(onClickItem:(pageCategory: PageCategory)->Unit)
= adapterDelegateViewBinding<Staff, NestedInfoInCategory, ItemFilmsBinding>({ layoutInflater, root ->
    ItemFilmsBinding.inflate(layoutInflater, root, false)
}) {
    binding.poster.setOnClickListener { onClickItem(PageCategory(CategoryInfo.STAFF, emptyList(),
        Query(id = item.staffId)
    )) }

    bind{
        binding.poster.glide(item.posterUrl)
        binding.filmsName.text = item.nameRu
        binding.genreName.text = item.description
    }
}