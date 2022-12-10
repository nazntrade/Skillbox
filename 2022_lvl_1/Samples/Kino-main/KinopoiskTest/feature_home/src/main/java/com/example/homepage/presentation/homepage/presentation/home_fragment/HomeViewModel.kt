package com.example.homepage.presentation.homepage.presentation.home_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.tools.*
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.all.LoadState
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.extensions.createListForView
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.repository.DataBaseRepository
import com.example.homepage.presentation.homepage.data.films.dto.CountryAndGenreDTO
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.basefrahment.BaseViewModel
import com.example.homepage.presentation.homepage.domain.NetworkCategoryImpl
import com.example.homepage.presentation.homepage.tools.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    private val networkRepository: NetworkCategoryImpl,
    private val counterGenreRepository: DataBaseRepository,
    private val collectionsRepository: CollectionsFilmsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val calendar = Calendar.getInstance()

    private var _homePageList =
        MutableStateFlow<List<BaseCategory>>(emptyList())
    val homePageList = _homePageList.asStateFlow()

    init {
        getHomePageList()
    }

    private fun getHomePageList() {
        viewModelScope.launch(handler + Dispatchers.IO) {
            _loadState.value = LoadState.LOADING
            loadAllFilms(
                calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1),
                getCounterAndGenreList()
            ).mergeHomeDatabase(counterGenreRepository, _homePageList, viewModelScope)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun createStartCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.addCollection("Любимое", ID_LIKE_COLLECTION)
            collectionsRepository.addCollection("Хочу посмотреть", ID_FAVORITE_COLLECTION)
            collectionsRepository.addCollection("Вам было интересно", ID_HISTORY_COLLECTION)
            collectionsRepository.addCollection("Просмотренно", ID_LOOK_COLLECTION)
        }
    }

    private suspend fun getCounterAndGenreList(): CountryAndGenreDTO {
        var counterAndGenre = CountryAndGenreDTO(
            counterGenreRepository.getAllCounter(),
            counterGenreRepository.getAllGenre()
        )

        if (counterAndGenre.countries.isEmpty() || counterAndGenre.genres.isEmpty())
            counterAndGenre = loadNetworkGenresAndCountries()

        return counterAndGenre

    }

    private suspend fun loadNetworkGenresAndCountries(): CountryAndGenreDTO {
        val counterAndGenre = networkRepository.getListGenreAndCounter()
        counterGenreRepository.insertCounter(counterAndGenre.countries)
        counterGenreRepository.insertGenre(counterAndGenre.genres)
        return counterAndGenre
    }

    private suspend fun loadAllFilms(
        year: Int,
        month: Int,
        counterAndGenre: CountryAndGenreDTO
    ): List<PageCategory> {
        val counter = counterAndGenre.getRandomCounter()
        val genre = counterAndGenre.getRandomGenre()
        val query = Query().apply {
            counterID = counter.id
            genreId = genre.id
            this.year = year
            this.month = month.converterInMonth()
        }

        val randomCategory  =  CategoryInfo.RANDOM.apply {
            text = counter.info +" "+ genre.info
        }

        return listOf(
            CategoryInfo.PREMIERS.createCategory(
                loadPremieres(networkRepository, query.year, query.month)
                    .createListForView(SIZE_LIST_VIEW), query
            ),
            CategoryInfo.TV_SERIES.createCategory(
                loadSerials(networkRepository, FIRST_PAGE).createListForView(
                    SIZE_LIST_VIEW
                )
            ),
            CategoryInfo.BEST.createCategory(
                loadBest(networkRepository, FIRST_PAGE).createListForView(SIZE_LIST_VIEW)
            ),
            CategoryInfo.POPULAR.createCategory(
                loadPopular(networkRepository, FIRST_PAGE).createListForView(SIZE_LIST_VIEW)
            ),
            randomCategory.createCategory(
                loadFilmsByCounterAdnGenre(networkRepository, FIRST_PAGE, query.counterID, query.genreId
                ).createListForView(SIZE_LIST_VIEW), query
            )
        )
    }

    fun navigateToCategory(categoryFilms: BaseCategory) {
        savedStateHandle[NAVIGATE__CATEGORY_PAGE] = categoryFilms as PageCategory
    }

    fun navigateToInfoFilm(filmID: Int?) {
        savedStateHandle[NAVIGATE__TO_INFO_FILM] = filmID
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}