package com.example.screen_listpage.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.tools.NAVIGATE__TO_INFO_FILM
import com.example.core.tools.SetSearch
import com.example.core.tools.all.LoadState
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.basefrahment.BaseViewModel
import com.example.screen_listpage.network.SearchPagingRepo
import com.example.screen_listpage.network.SearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchStartViewModel(
    private val networkRepository: SearchRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
    private val searchPagingRepo: SearchPagingRepo
) : BaseViewModel() {

    private val _films = MutableSharedFlow<List<NestedInfoInCategory>>()
    val films = _films.asSharedFlow()

    init {
        viewModelScope.launch{
            _loadState.emit(LoadState.SUCCESS)
        }

    }

    var keyWordStartSearch = ""

    fun search(keyWord: String, after: Int) {
        viewModelScope.launch(handler+ Dispatchers.IO) {
            _loadState.emit(LoadState.LOADING)
            keyWordStartSearch = keyWord
            delay(500)
            if (keyWordStartSearch == "") _films.emit(emptyList())
            if (keyWordStartSearch.length == after && keyWordStartSearch.isNotEmpty())
                _films.emit((networkRepository.getSearchFilms(SetSearch, keyWordStartSearch, 1).items))
            _loadState.emit(LoadState.SUCCESS)
        }
    }

    fun navigate(pageCategory: PageCategory) {
        savedStateHandle[NAVIGATE__TO_INFO_FILM] = pageCategory.query?.id
    }
}
