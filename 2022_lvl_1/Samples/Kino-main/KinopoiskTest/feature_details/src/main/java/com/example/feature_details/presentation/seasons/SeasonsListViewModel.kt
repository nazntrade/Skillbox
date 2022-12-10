package com.example.feature_details.presentation.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.tools.NAVIGATE__TO_SEASONS
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.season.Season

class SeasonsListViewModel(
   private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val list: List<NestedInfoInCategory>? = savedStateHandle[NAVIGATE__TO_SEASONS]

    fun getListInfo()  = if (list != null) list as List<Season>
    else emptyList()

    fun createTabInfo(position:Int) {
        list as List<Season>
        savedStateHandle[SEASONS_TAB_lIST] = list[position].episodes
    }

    companion object{
        const val SEASONS_TAB_lIST ="SEASONS_TAB_lIST"
    }

}