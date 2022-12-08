package ru.zhdanon.skillcinema.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.zhdanon.skillcinema.data.ParamsFilterFilm
import ru.zhdanon.skillcinema.data.filmbyfilter.FilterCountry
import ru.zhdanon.skillcinema.data.filmbyfilter.FilterGenre
import ru.zhdanon.skillcinema.domain.GetFilmListUseCase
import ru.zhdanon.skillcinema.domain.GetGenresCountriesUseCase
import ru.zhdanon.skillcinema.entity.HomeItem
import ru.zhdanon.skillcinema.ui.filmsbyfilter.FilmsByFilterPagingSource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getGenresCountriesUseCase: GetGenresCountriesUseCase
) : ViewModel() {

    private var filters = ParamsFilterFilm()
    private val _isFilterChanged = MutableStateFlow(false)
    val isFilterChanged = _isFilterChanged.asStateFlow()


    private val _countries = MutableStateFlow<List<FilterCountry>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _genres = MutableStateFlow<List<FilterGenre>>(emptyList())
    val genres = _genres.asStateFlow()

    val films: Flow<PagingData<HomeItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            FilmsByFilterPagingSource(
                filters = filters,
                getFilmListUseCase = getFilmListUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    init {
        getCountriesOrGenres()
    }

    fun getFilters() = filters

    fun updateFilters(filterFilm: ParamsFilterFilm) {
        _isFilterChanged.value = false
        filters = filterFilm
        _isFilterChanged.value = true
    }

    private fun getCountriesOrGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.value =
                getGenresCountriesUseCase.executeGenresCountries().countries.sortedBy { it.name }
            _genres.value =
                getGenresCountriesUseCase.executeGenresCountries().genres.sortedBy { it.name }
        }
    }
}