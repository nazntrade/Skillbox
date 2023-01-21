package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.*
import com.becker.beckerSkillCinema.domain.*
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel.Companion.currentMonth
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel.Companion.currentYear
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allFilmAdapters.AllFilmPagingSource
import com.becker.beckerSkillCinema.presentation.filmsByFilter.FilmsByFilterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllFilmsViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getFilmListUseCase: GetFilmListUseCase,
) : ViewModel() {

    private val repository = CinemaRepository()

    private var _localCategory: CategoriesFilms? = null
    val localCategory
        get() = _localCategory

    init {
        getCategory()
        getPagedFilms()
    }

    fun putFilmId(filmId: Int) {
        repository.putFilmId(filmId)
    }

    private var _pagedFilms: Flow<PagingData<HomeItem>>? = null
    val pagedFilms
        get() = _pagedFilms

    fun getPagedFilms() {
        if (localCategory == CategoriesFilms.TV_SERIES) {
            _pagedFilms = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    FilmsByFilterPagingSource(
                        filters = ParamsFilterFilm(
                            type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)
                        ),
                        getFilmListUseCase = getFilmListUseCase
                    )
                }
            ).flow.cachedIn(viewModelScope)
        } else {
            _pagedFilms = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    AllFilmPagingSource(
                        categoriesFilms = _localCategory!!,
                        year = currentYear,
                        month = currentMonth,
                        getPremierFilmUseCase,
                        getTopFilmsUseCase,
                        getFilmListUseCase
                    )
                }
            ).flow.cachedIn(viewModelScope)
        }
    }

    fun getCategory() {
        val category = repository.getCurrentCategory()
        if (category != null) {
            if (_localCategory != category) {
                _localCategory = category
            }
        }
    }
}