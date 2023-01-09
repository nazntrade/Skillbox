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

    private var _localCategoryLiveData: CategoriesFilms? = null
    val localCategoryLiveData
        get() = _localCategoryLiveData

    init {
        getCategory()
        getPagedFilms()
    }


    var pagedFilms: Flow<PagingData<HomeItem>>? = null

    fun getPagedFilms() {
        if (localCategoryLiveData == CategoriesFilms.TV_SERIES) {
            pagedFilms = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    FilmsByFilterPagingSource(
                        filters = ParamsFilterFilm(type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)),
                        getFilmListUseCase = getFilmListUseCase
                    )
                }
            ).flow.cachedIn(viewModelScope)
        } else {
            pagedFilms = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    AllFilmPagingSource(
                        categoriesFilms =
                        _localCategoryLiveData!!,
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
            if (_localCategoryLiveData != category) {
                _localCategoryLiveData =
                    category
            }
        }
    }
}