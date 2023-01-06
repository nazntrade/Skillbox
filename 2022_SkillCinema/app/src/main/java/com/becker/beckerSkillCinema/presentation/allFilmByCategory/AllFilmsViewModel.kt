package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.ParamsFilterFilm
import com.becker.beckerSkillCinema.data.TOP_TYPES
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

    // FragmentAllFilms
    fun setAllFilmsByCategory(    // ????????????????????????????????????????????????????????????????
        currentCategory: CategoriesFilms
    ): Flow<PagingData<HomeItem>> {
        val allFilmsByCategory: Flow<PagingData<HomeItem>> = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                AllFilmPagingSource(
                    categoriesFilms = currentCategory,
                    year = currentYear,
                    month = currentMonth,
                    getPremierFilmUseCase,
                    getTopFilmsUseCase,
                    getFilmListUseCase
                )
            }
        ).flow.cachedIn(viewModelScope) //????????????????????
        return allFilmsByCategory
    }

    fun setAllSeries(): Flow<PagingData<HomeItem>> {   // ???????????????????????????????????????????
        val allSeries: Flow<PagingData<HomeItem>> = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                FilmsByFilterPagingSource(
                    filters = ParamsFilterFilm(type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)),
                    getFilmListUseCase = getFilmListUseCase
                )
            }
        ).flow.cachedIn(viewModelScope) //?????????????????????
        return allSeries
    }


}