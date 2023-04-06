package com.becker.beckerSkillCinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import com.becker.beckerSkillCinema.data.ParamsFilterFilm
import com.becker.beckerSkillCinema.data.SearchParamsPeopleOrFilm
import com.becker.beckerSkillCinema.data.filmByFilter.FilterCountry
import com.becker.beckerSkillCinema.data.filmByFilter.FilterGenre
import com.becker.beckerSkillCinema.data.personFromSearch.PeopleFromSearch
import com.becker.beckerSkillCinema.domain.GetFilmListUseCase
import com.becker.beckerSkillCinema.domain.GetGenresCountriesUseCase
import com.becker.beckerSkillCinema.domain.GetPeopleFromSearchUseCase
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.home.allFilmsByCategory.allFilmAdapters.FilmsByFilterPagingSource
import com.becker.beckerSkillCinema.utils.ConstantsAndParams
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
    private val getGenresCountriesUseCase: GetGenresCountriesUseCase,
    private val getPeopleFromSearchUseCase: GetPeopleFromSearchUseCase
) : ViewModel() {

    private val repository = CinemaRepository()

    private var filters =
        ParamsFilterFilm(order = SearchSettingsFragment.Companion.Order.NUM_VOTE.text)

    private var searchType = ConstantsAndParams.TYPE_FILM

    private val _isFilterChanged = MutableStateFlow(false)
    val isFilterChanged = _isFilterChanged.asStateFlow()

    private val _countries = MutableStateFlow<List<FilterCountry>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _genres = MutableStateFlow<List<FilterGenre>>(emptyList())
    val genres = _genres.asStateFlow()

    private var _pagedFilms: Flow<PagingData<HomeItem>>? = null
    val pagedFilms
        get() = _pagedFilms

    private val _peopleFromSearch = MutableStateFlow<List<PeopleFromSearch>>(emptyList())
    val peopleFromSearch = _peopleFromSearch.asStateFlow()

    private val _loadingState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadingState = _loadingState.asStateFlow()

    init {
        getCountriesOrGenres()
        getFilms()
    }

    fun putFilmId(filmId: Int) {
        repository.putFilmId(filmId)
    }

    fun getFilms() {
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
                Timber.e("getFilms error: $e")
            }
        }
    }

    fun getFilters() = filters
    fun getSearchType() = searchType

    fun getPeople() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingState.value = StateLoading.Loading
                val peopleList = getPeopleFromSearchUseCase
                    .executePeopleFromSearch(getFilters().keyword, page = 1).items
                _peopleFromSearch.value = peopleList
                _loadingState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadingState.value = StateLoading.Error(e.message.toString())
                Timber.e("getPeople $e")
            }
        }
    }

    fun updateFilters(filterFilm: ParamsFilterFilm) {
        _isFilterChanged.value = false
        filters = filterFilm
        _isFilterChanged.value = true
    }

    fun updateSearchType(newType: SearchParamsPeopleOrFilm) {
        searchType = newType
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