package com.example.feature_details.presentation.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.tools.base_model.category.season.Episode
import com.example.feature_details.presentation.seasons.SeasonsListViewModel.Companion.SEASONS_TAB_lIST

class SeasonsViewModel(
    savedStateHandle: SavedStateHandle
):ViewModel() {

   private val list:List<Episode>? = savedStateHandle[SEASONS_TAB_lIST]

    fun getListInfo() = list?: emptyList()
}