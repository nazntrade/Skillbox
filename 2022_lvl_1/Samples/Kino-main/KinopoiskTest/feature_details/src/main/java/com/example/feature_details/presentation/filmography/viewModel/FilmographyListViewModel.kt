package com.example.feature_details.presentation.filmography.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.tools.NAVIGATE__TO_FILMOGRAPHY
import com.example.feature_details.data.FilmographyCategory
import com.example.feature_details.data.details_staff.FilmView

class FilmographyListViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val films: List<FilmView>? = savedStateHandle[NAVIGATE__TO_FILMOGRAPHY]


    fun divideByCategory(): MutableList<FilmographyCategory> {
        val list = mutableListOf<FilmographyCategory>()

        FilmographyCategory.values().forEach { it.items = mutableListOf() }

        films?.forEach { FilmographyCategory.valueOf(it.professionKey).items.add(it) }


        FilmographyCategory.values().forEach { if (it.items.isNotEmpty()) list.add(it) }
        savedStateHandle[FILMOGRAPHY_CATEGORY] = list
        return list
    }

    fun onCategoryClick(position: Int) {
        savedStateHandle[FILMOGRAPHY_CATEGORY_POSITION] = position
    }

    companion object {
        const val FILMOGRAPHY_CATEGORY = "Filmography_category"
        const val FILMOGRAPHY_CATEGORY_POSITION = "Filmography_category_position"
    }
}