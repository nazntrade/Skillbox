package com.example.core.tools.adapter.delegate

import com.example.core.databinding.ItemSettingSearchBinding
import com.example.core.tools.SetSearch
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.TypeSettings
import com.example.core.tools.general.GenreCountry
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateSettingsSearch(
    settings: TypeSettings?=null,
    onClickItem: (pageCategory: PageCategory) -> Unit,

) =
    adapterDelegateViewBinding<GenreCountry, NestedInfoInCategory, ItemSettingSearchBinding>({ layoutInflater, root ->
        ItemSettingSearchBinding.inflate(layoutInflater, root, false)
    }) {

        binding.textItem.setOnClickListener {
            onClickItem(PageCategory(CategoryInfo.RANDOM, emptyList(), Query(id = item.id)))
        }

        bind {
            binding.textItem.text = item.info
            when (settings){
                TypeSettings.COUNTRY -> binding.textItem.isSelected = item.id == SetSearch.counterID
                TypeSettings.GENRE -> binding.textItem.isSelected = item.id == SetSearch.genreId
                else->{}
            }
        }
    }

