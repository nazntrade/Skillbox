package com.example.core.tools.adapter.delegate

import com.example.core.databinding.ItemDescriptionBinding
import com.example.core.databinding.ItemSeasonsBinding
import com.example.core.databinding.ItemSeasonsInfoBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.base_model.category.SeasonsCategory
import com.example.core.tools.base_model.category.season.Episode
import com.example.core.tools.category.CategoryInfo
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateSeasons(onClickItem:(pageCategory:PageCategory)->Unit) =
adapterDelegateViewBinding<SeasonsCategory, BaseCategory, ItemSeasonsBinding>({ layoutInflater, root ->
    ItemSeasonsBinding.inflate(layoutInflater, root, false)
}) {

    binding.root.setOnClickListener{
        onClickItem(PageCategory(CategoryInfo.TV_SERIES, item.items))
    }

    bind{

        var episode = 0
        item.items.forEach {
           episode+= it.episodes.size
        }
        binding.info.text = item.items.size.toString() + " сезон, "+episode +"серий"
    }
}

fun delegateEpisodes() =
    adapterDelegateViewBinding<Episode, NestedInfoInCategory, ItemSeasonsInfoBinding>({ layoutInflater, root ->
        ItemSeasonsInfoBinding.inflate(layoutInflater, root, false)
    }) {


        bind{
            binding.name.text = item.episodeNumber.toString() +"серия. "+item.nameEn
            binding.date.text = item.releaseDate
        }
    }

