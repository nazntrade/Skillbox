package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.*
import com.becker.beckerSkillCinema.domain.*
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel.Companion.currentMonth
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel.Companion.currentYear
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allFilmAdapters.AllFilmPagingSource
import com.becker.beckerSkillCinema.presentation.filmsByFilter.FilmsByFilterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFilmsViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getFilmListUseCase: GetFilmListUseCase,
) : ViewModel() {

    private val repository = CinemaRepository()
    private var localCategory: CategoriesFilms? = null

//    private val _allFilms = MutableStateFlow<PagingData<HomeItem>>(PagingData.empty())
//    val allFilms = _allFilms.asStateFlow()

    init {
        getCategory()
//        setAllFilmsByCategory()
//        setAllSeries()
        getPagedFilms()
        getPagedSeries()
    }

    var pagedFilms: Flow<PagingData<HomeItem>>? = null
    var pagedSeries: Flow<PagingData<HomeItem>>? = null

    private fun getPagedSeries(){
        getCategory()
        pagedSeries = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                FilmsByFilterPagingSource(
                    filters = ParamsFilterFilm(type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)),
                    getFilmListUseCase = getFilmListUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun getPagedFilms(){
        getCategory()
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                AllFilmPagingSource(
                    categoriesFilms =
                    localCategory!!,
                    year = currentYear,
                    month = currentMonth,
                    getPremierFilmUseCase,
                    getTopFilmsUseCase,
                    getFilmListUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }


    fun getCategory() {
        val category = repository.getCurrentCategory()
        if (category != null)
            localCategory =
                category
    }

//    // FragmentAllFilms
//    fun setAllFilmsByCategory(    // ????????????????????????????????????????????????????????????????
////        currentCategory: CategoriesFilms
//    ): Flow<PagingData<HomeItem>> {
//        getCategory()
//        val allFilmsByCategory: Flow<PagingData<HomeItem>> = Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = {
//                AllFilmPagingSource(
//                    categoriesFilms =
//                    localCategory!!,
//                    year = currentYear,
//                    month = currentMonth,
//                    getPremierFilmUseCase,
//                    getTopFilmsUseCase,
//                    getFilmListUseCase
//                )
//            }
//        ).flow.cachedIn(viewModelScope) //????????????????????
//        return allFilmsByCategory
//    }
//
//    fun setAllSeries(): Flow<PagingData<HomeItem>> {   // ???????????????????????????????????????????
//        getCategory()
//        val allSeries: Flow<PagingData<HomeItem>> = Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = {
//                FilmsByFilterPagingSource(
//                    filters = ParamsFilterFilm(type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)),
//                    getFilmListUseCase = getFilmListUseCase
//                )
//            }
//        ).flow.cachedIn(viewModelScope) //?????????????????????
//        return allSeries
//    }
//

}