package com.becker.beckerSkillCinema.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.*
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.presentation.filmsByFilter.FilmsByFilterPagingSource
import com.becker.beckerSkillCinema.data.seasons.Season
import com.becker.beckerSkillCinema.data.similarFilm.SimilarItem
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.domain.*
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allFilmAdapters.AllFilmPagingSource
import com.becker.beckerSkillCinema.presentation.gallery.GalleryFullPagingSource
import com.becker.beckerSkillCinema.utils.toLimitTheNumberOfObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Month
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val getActorsByFilmIdUseCase: GetActorsListUseCase,
    private val getGalleryByIdUseCase: GetGalleryByIdUseCase,
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getSeasonsUseCase: GetSeasonsUseCase,

    ) : ViewModel() {

    private val currentMonth: String = Month.of(calendar.get(Calendar.MONTH) + 1).name
    private val currentYear: Int = calendar.get(Calendar.YEAR)
    private var currentFilmId = 723959


    // FragmentHome
    private val _homePageFilmList = MutableStateFlow<List<HomeList>>(emptyList())
    val homePageFilmList = _homePageFilmList.asStateFlow()

    private val _loadCategoryState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCategoryState = _loadCategoryState.asStateFlow()

    init {
        getFilmsByCategories()
    }

    fun getFilmsByCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCategoryState.value = StateLoading.Loading
                val homeLists = listOf(
                    HomeList(
                        category = CategoriesFilms.PREMIERS,
                        filmList = getPremierFilmUseCase.executePremieres(
                            year = currentYear,
                            month = currentMonth
                        ).toLimitTheNumberOfObjects(20)
                    ),
                    HomeList(
                        category = CategoriesFilms.POPULAR_100,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.POPULAR_100),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.BEST_250,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.BEST_250),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.TV_SERIES,
                        filmList = getFilmListUseCase.executeFilmsByFilter(
                            filters = ParamsFilterFilm(
                                type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES),
                                ratingFrom = 7
                            ),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.MOST_AWAIT,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.MOST_AWAIT),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.BIOGRAPHY,
                        filmList = getFilmListUseCase.executeFilmsByFilter(
                            filters = ParamsFilterFilm(
                                type = TOP_TYPES.getValue(CategoriesFilms.BIOGRAPHY),
                                genres = GENRE_INT_FOR_FILTER.getValue(CategoriesFilms.BIOGRAPHY),
                                ratingFrom = 7
                            ),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.SCIENCE_FICTION,
                        filmList = getFilmListUseCase.executeFilmsByFilter(
                            filters = ParamsFilterFilm(
                                type = TOP_TYPES.getValue(CategoriesFilms.SCIENCE_FICTION),
                                genres = GENRE_INT_FOR_FILTER.getValue(CategoriesFilms.SCIENCE_FICTION),
                                ratingFrom = 7
                            ),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.CARTOONS,
                        filmList = getFilmListUseCase.executeFilmsByFilter(
                            filters = ParamsFilterFilm(
                                type = TOP_TYPES.getValue(CategoriesFilms.CARTOONS),
                                genres = GENRE_INT_FOR_FILTER.getValue(CategoriesFilms.CARTOONS),
                                ratingFrom = 7
                            ),
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


    // FragmentFilmDetail
    private val _currentFilm = MutableStateFlow<ResponseCurrentFilm?>(null)
    val currentFilm = _currentFilm.asStateFlow()

    private val _seasons = MutableStateFlow<List<Season>>(emptyList())
    val seasons = _seasons.asStateFlow()

    private val _currentFilmActors = MutableStateFlow<List<ResponseStaffByFilmId>>(emptyList())
    val currentFilmActors = _currentFilmActors.asStateFlow()

    private val _currentFilmMakers = MutableStateFlow<List<ResponseStaffByFilmId>>(emptyList())
    val currentFilmMakers = _currentFilmMakers.asStateFlow()

    private val _currentFilmGallery = MutableStateFlow<List<ItemImageGallery>>(emptyList())
    val currentFilmGallery = _currentFilmGallery.asStateFlow()

    private val _currentFilmSimilar = MutableStateFlow<List<SimilarItem>>(emptyList())
    val currentFilmSimilar = _currentFilmSimilar.asStateFlow()

    private val _loadCurrentFilmState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCurrentFilmState = _loadCurrentFilmState.asStateFlow()

    fun getFilmById(filmId: Int) {
        currentFilmId = filmId
        updateParamsFilterGallery()
        viewModelScope.launch {
            try {
                _loadCurrentFilmState.value = StateLoading.Loading
                // film
                val tempFilm = getFilmByIdUseCase.executeFilmById(filmId)
                _currentFilm.value = tempFilm
                // staffs
                val tempActorList = getActorsByFilmIdUseCase.executeActorsList(filmId)
                sortingActorsAndMakers(tempActorList)
                // gallery
                setGalleryCount(filmId)
                _currentFilmGallery.value =
                    getGalleryByIdUseCase.executeGalleryByFilmId(
                        currentParamsFilterGallery.filmId,
                        currentParamsFilterGallery.galleryType,
                        1
                    ).items
                // similar
                val responseSimilar = getSimilarFilmsUseCase.executeSimilarFilms(filmId)
                if (responseSimilar.total != 0) _currentFilmSimilar.value = responseSimilar.items!!
                _loadCurrentFilmState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadCurrentFilmState.value = StateLoading.Error(e.message.toString())
            }
        }
    }

    fun getSeasons(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seasons.value = getSeasonsUseCase.executeSeasons(seriesId).items
        }
    }

    private fun sortingActorsAndMakers(actorsList: List<ResponseStaffByFilmId>) {
        val actors = mutableListOf<ResponseStaffByFilmId>()
        val makers = mutableListOf<ResponseStaffByFilmId>()
        actorsList.forEach {
            if (it.professionKey == "ACTOR") actors.add(it)
            else makers.add(it)
        }
        _currentFilmActors.value = actors
        _currentFilmMakers.value = makers
    }


    // FragmentGallery
    private val _galleryCount = MutableStateFlow(0)
    val galleryCount = _galleryCount.asStateFlow()

    private val _galleryChipList = MutableStateFlow<Map<String, Int>>(emptyMap())
    val galleryChipList = _galleryChipList.asStateFlow()

    val galleryByType: Flow<PagingData<ItemImageGallery>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
//            Log.d(
//                TAG,
//                "CVM-235: galleryByType: ${currentParamsFilterGallery.filmId} | ${currentParamsFilterGallery.galleryType}"
//            )
            GalleryFullPagingSource(
                getGalleryByIdUseCase = getGalleryByIdUseCase,
                filterParams = currentParamsFilterGallery
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun setGalleryCount(filmId: Int) {
        _galleryCount.value = 0
        val tempChipsList = mutableMapOf<String, Int>()
        var countImages = 0
        viewModelScope.launch(Dispatchers.IO) {
            GALLERY_TYPES.forEach {
                val temp = getGalleryByIdUseCase.executeGalleryByFilmId(filmId, it.key, 1)
                tempChipsList[it.key] = temp.total
                countImages += temp.total
            }
            _galleryCount.value = countImages
            _galleryChipList.value = tempChipsList
        }
    }


    fun updateParamsFilterGallery(filmId: Int = currentFilmId, galleryType: String = "STILL") {
        currentParamsFilterGallery =
            currentParamsFilterGallery.copy(filmId = filmId, galleryType = galleryType)
    }


    // FragmentStaffDetail

    companion object {
        private val calendar = Calendar.getInstance()

        private var currentParamsFilterGallery = ParamsFilterGallery(
            filmId = 723959,
            galleryType = "STILL"
        )

    }
}