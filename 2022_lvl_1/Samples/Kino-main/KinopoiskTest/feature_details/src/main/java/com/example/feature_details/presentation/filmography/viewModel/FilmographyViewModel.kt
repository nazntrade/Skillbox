package com.example.feature_details.presentation.filmography.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.tools.NAVIGATE__TO_INFO_FILM
import com.example.core.tools.all.LoadState
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.films.FilmographyMove
import com.example.core.tools.basefrahment.BaseViewModel
import com.example.feature_details.data.FilmographyCategory
import com.example.feature_details.domain.repository_ipl.NetworkFilmDetailsRepositoryImpl
import com.example.feature_details.presentation.filmography.viewModel.FilmographyListViewModel.Companion.FILMOGRAPHY_CATEGORY
import com.example.feature_details.presentation.filmography.viewModel.FilmographyListViewModel.Companion.FILMOGRAPHY_CATEGORY_POSITION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FilmographyViewModel(
    private val networkRepository: NetworkFilmDetailsRepositoryImpl,
    private val  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _poster = MutableSharedFlow<Pair<List<FilmographyMove>, Int>>()
    val poster = _poster.asSharedFlow()

    private val categoryList = savedStateHandle.get<MutableList<FilmographyCategory>>(FILMOGRAPHY_CATEGORY)
    private val position = savedStateHandle.get<Int>(FILMOGRAPHY_CATEGORY_POSITION)

    private val category = position?.let { categoryList?.get(it) }

    init {
        viewModelScope.launch {
            _loadState.emit(LoadState.SUCCESS)
        }
    }

    fun onClick(position: Int?) {

        viewModelScope.launch(handler+ Dispatchers.IO) {

            _loadState.emit(LoadState.LOADING)

            if (category != null && position!=null) {

                if (category.items[position].posterURL != null)

                    category.items[position].posterURL ="" // без этогот всегда возвращем null

                category.items[position].isExpand =category.items[position].isExpand

                category.items[position].posterURL =
                    networkRepository.getFilmForID(category.items[position].filmId).posterUrl

                category.items[position].isExpand = !category.items[position].isExpand
                _poster.emit(Pair(category.items, position))
            }
            _loadState.emit(LoadState.SUCCESS)
        }
    }

    fun startList() = category!!.items as List<NestedInfoInCategory>

    fun navigate(id: Int?) {
        savedStateHandle[NAVIGATE__TO_INFO_FILM] = id
    }
}