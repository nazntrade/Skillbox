package com.becker.beckerSkillCinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.HomeList
import com.becker.beckerSkillCinema.data.TOP_TYPES
import com.becker.beckerSkillCinema.domain.GetPremierFilmUseCase
import com.becker.beckerSkillCinema.domain.GetTopFilmsUseCase
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.utils.toLimitTheNumberOfObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Month
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
) : ViewModel() {

    // FragmentHome
    private val _homePageFilmList = MutableStateFlow<List<HomeList>>(emptyList())
    val homePageFilmList = _homePageFilmList.asStateFlow()

    private val _loadCategoryState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCategoryState = _loadCategoryState.asStateFlow()

    fun getFilmsByCategories(
        // ???????????????????????????????????????????????????????????????????????????????????????
        year: Int = calendar.get(Calendar.YEAR),
        month: String = Month.of(calendar.get(Calendar.MONTH) + 1).name
    ) {
        Timber.d("CURRENT_MONTH_TEST: number: ${calendar.get(Calendar.MONTH) + 1}, name: $month")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCategoryState.value = StateLoading.Loading
                val homeLists = listOf(
                    HomeList(
                        category = CategoriesFilms.PREMIERS,
                        filmList = getPremierFilmUseCase.executePremieres(
                            year = year,
                            month = month
                        ).toLimitTheNumberOfObjects(20)
                    ),
                    HomeList(
                        category = CategoriesFilms.POPULAR_100,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.POPULAR_100),
                            page = 1
                        )
                    )
                )
                _homePageFilmList.value = homeLists
                _loadCategoryState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadCategoryState.value = StateLoading.Error(e.message.toString())
            }
        }
    }

    // FragmentAllFilms

    // FragmentFilmDetail

    // FragmentGallery

    // FragmentStaffDetail

    companion object {
        private val calendar = Calendar.getInstance()
    }
}