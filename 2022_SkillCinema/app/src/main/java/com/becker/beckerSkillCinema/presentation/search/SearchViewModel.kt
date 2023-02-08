package com.becker.beckerSkillCinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.ParamsFilterFilm
import com.becker.beckerSkillCinema.data.TOP_TYPES
import com.becker.beckerSkillCinema.data.filmByFilter.FilterCountry
import com.becker.beckerSkillCinema.data.filmByFilter.FilterGenre
import com.becker.beckerSkillCinema.domain.GetFilmListUseCase
import com.becker.beckerSkillCinema.domain.GetGenresCountriesUseCase
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.home.allFilmsByCategory.allFilmAdapters.FilmsByFilterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getGenresCountriesUseCase: GetGenresCountriesUseCase
) : ViewModel() {

    private val repository = CinemaRepository()

    private var filters = ParamsFilterFilm(
        genres = mapOf(
            6 to TOP_TYPES.getValue(CategoriesFilms.SCIENCE_FICTION)
        )
    )

    private val _isFilterChanged = MutableStateFlow(false)
    val isFilterChanged = _isFilterChanged.asStateFlow()

    private val _countries = MutableStateFlow<List<FilterCountry>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _genres = MutableStateFlow<List<FilterGenre>>(emptyList())
    val genres = _genres.asStateFlow()

    init {
        getCountriesOrGenres()
        getFilms()
    }

    fun putFilmId(filmId: Int) {
        repository.putFilmId(filmId)
    }

    private var _pagedFilms: Flow<PagingData<HomeItem>>? = null
    val pagedFilms
        get() = _pagedFilms

    private fun getFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _pagedFilms = Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        FilmsByFilterPagingSource(
                            filters = filters,
                            getFilmListUseCase = getFilmListUseCase
                        )
                    }
                ).flow
            } catch (e: Throwable) {
                Timber.e("getCountriesOrGenres $e")
            }
        }
    }

    fun getFilters() = filters

    fun updateFilters(filterFilm: ParamsFilterFilm) {
        _isFilterChanged.value = false
        filters = filterFilm
        _isFilterChanged.value = true
    }

    private fun getCountriesOrGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempGenreCountry = getGenresCountriesUseCase.executeGenresCountries()
                _countries.value = tempGenreCountry.countries.sortedBy { it.name }
                _genres.value = tempGenreCountry.genres.sortedBy { it.name }
            } catch (e: Throwable) {
                Timber.e("getCountriesOrGenres $e")
            }
        }
    }
}