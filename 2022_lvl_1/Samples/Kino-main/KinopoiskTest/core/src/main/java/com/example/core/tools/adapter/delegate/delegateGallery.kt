package com.example.core.tools.adapter.delegate

import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.example.core.databinding.ItemFullGalleryBinding
import com.example.core.databinding.ItemGalleryBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.BaseGallery
import com.example.core.tools.base_model.FullGalleryImage
import com.example.core.tools.base_model.category.GriDGallery
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateGallery( onClickItem:(pageCategory:PageCategory)->Unit )
= adapterDelegateViewBinding<BaseGallery, NestedInfoInCategory, ItemGalleryBinding>({ layoutInflater, root ->
    ItemGalleryBinding.inflate(layoutInflater, root, false)
}) {

    binding.poster.setOnClickListener {
        onClickItem(PageCategory(CategoryInfo.GALLERY, emptyList(), Query(id = absoluteAdapterPosition)))
    }

    bind{
        binding.poster.glide(item.previewUrl)
    }
}

fun delegateGalleryFull( onClickItem:(pageCategory:PageCategory)->Unit )
= adapterDelegateViewBinding<FullGalleryImage, NestedInfoInCategory, ItemFullGalleryBinding>({ layoutInflater, root ->
    ItemFullGalleryBinding.inflate(layoutInflater, root, false)
}) {



    bind{
        binding.itemCard
            .layoutParams.width = LayoutParams.MATCH_PARENT
        binding.itemCard
            .layoutParams.height = 800
        binding.poster.glide(item.imageUrl)
    }
}

fun delegateGridGallery( onClickItem:(pageCategory:PageCategory)->Unit )
        = adapterDelegateViewBinding<GriDGallery, NestedInfoInCategory, ItemGalleryBinding>({ layoutInflater, root ->
    ItemGalleryBinding.inflate(layoutInflater, root, false)
}) {

    binding.poster.setOnClickListener {
        if (item.previewUrl=="Next") onClickItem(PageCategory(CategoryInfo.GALLERY, emptyList(),Query(id=-1)))
        else onClickItem(PageCategory(CategoryInfo.GALLERY, emptyList(),Query(id=absoluteAdapterPosition)))
    }

    bind{
        binding.itemCard
            .layoutParams.width = LayoutParams.MATCH_PARENT
        binding.poster.glide(item.imageUrl)
    }
}