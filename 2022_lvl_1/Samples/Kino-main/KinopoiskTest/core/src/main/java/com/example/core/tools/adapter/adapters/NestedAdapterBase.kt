package com.example.core.tools.adapter.adapters

import com.example.core.tools.adapter.delegate.*
import com.example.core.tools.adapter.diffutil.NestedItemDiffUtil
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.BaseDetailsCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.TypeSettings
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class NestedAdapterBase(
    onClickItem: (pageCategory: PageCategory) -> Unit,
    onClickAll: (startCategory: BaseDetailsCategory) -> Unit,
    settingType: TypeSettings? = null
) : AsyncListDifferDelegationAdapter<NestedInfoInCategory>(NestedItemDiffUtil()) {
    init {

        delegatesManager.setFallbackDelegate(delegateStun())
            .addDelegate(delegateGalleryFull(onClickItem))
            .addDelegate(delegateGridGallery {
                onClickItem(PageCategory(CategoryInfo.GALLERY, items, it.query)) })
            .addDelegate(delegateGallery {
                onClickItem(PageCategory(CategoryInfo.GALLERY, items, it.query)) })
            .addDelegate(delegateBaseFilms { onClickItem(it) })
            .addDelegate(filmographyDelegate({ onClickItem(it) }, { onClickAll(it) }))
            .addDelegate(delegateNext { onClickAll(it) })
            .addDelegate(delegateStaff { onClickItem(it) })
            .addDelegate(delegateSettingsSearch(settingType) { onClickItem(it) })
            .addDelegate(collectionsDelegate {onClickItem(it)})
            .addDelegate(delegateSearchFilms {onClickItem(it)})
            .addDelegate(delegateEpisodes ())
    }

}