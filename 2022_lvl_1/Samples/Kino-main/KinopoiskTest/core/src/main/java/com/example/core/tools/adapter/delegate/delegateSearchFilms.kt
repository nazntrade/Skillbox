package com.example.core.tools.adapter.delegate

import android.annotation.SuppressLint
import com.example.core.databinding.ItemFilmsSearchBinding
import com.example.core.databinding.ItemSettingSearchBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.base_model.films.SearchFilms
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

@SuppressLint("SetTextI18n")
fun delegateSearchFilms(onClickItem:(pageCategory:PageCategory)->Unit ) =
    adapterDelegateViewBinding<SearchFilms, NestedInfoInCategory, ItemFilmsSearchBinding>({ layoutInflater, root ->
        ItemFilmsSearchBinding.inflate(layoutInflater, root, false)
    }) {

        binding.root.setOnClickListener{
            onClickItem(PageCategory(CategoryInfo.BEST, emptyList(), Query(id = item.filmId)))
        }

        bind {
            binding.poster.glide(item.posterUrlPreview)
            binding.rating.text = item.rating
            binding.name.text = if (item.nameRu != null) item.nameRu
            else item.nameEn
            binding.description.text = "${item.year}, ${item.genres.first()}"
        }
    }