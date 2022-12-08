package ru.zhdanon.skillcinema.ui

import android.util.Log
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
import ru.zhdanon.skillcinema.app.converterInMonth
import ru.zhdanon.skillcinema.app.prepareToShow
import ru.zhdanon.skillcinema.data.*
import ru.zhdanon.skillcinema.data.filmbyid.ResponseCurrentFilm
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery
import ru.zhdanon.skillcinema.data.seasons.Season
import ru.zhdanon.skillcinema.data.similarfilm.SimilarItem
import ru.zhdanon.skillcinema.data.staffbyfilmid.ResponseStaffByFilmId
import ru.zhdanon.skillcinema.data.staffbyid.ResponseStaffById
import ru.zhdanon.skillcinema.domain.*
import ru.zhdanon.skillcinema.entity.HomeItem
import ru.zhdanon.skillcinema.ui.allfilmsbycategory.allfilmadapter.AllFilmAdapter
import ru.zhdanon.skillcinema.ui.allfilmsbycategory.allfilmadapter.AllFilmPagingSource
import ru.zhdanon.skillcinema.ui.filmsbyfilter.FilmsByFilterPagingSource
import ru.zhdanon.skillcinema.ui.gallery.recycleradapter.GalleryFullPagingSource
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CinemaViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val getActorsByFilmIdUseCase: GetActorsListUseCase,
    private val getGalleryByIdUseCase: GetGalleryByIdUseCase,
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getSeasonsUseCase: GetSeasonsUseCase,
    private val getStaffByIdUseCase: GetStaffByIdUseCase
) : ViewModel() {

    private var currentFilmId = 328

    init {
        getFilmsByCategories()
    }

    // FragmentHome
    private val _homePageList = MutableStateFlow<List<HomeList>>(emptyList())
    val homePageList = _homePageList.asStateFlow()

    private val _loadCategoryState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCategoryState = _loadCategoryState.asStateFlow()

    val allFilmsByCategory: Flow<PagingData<HomeItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            AllFilmPagingSource(
                filterParams = currentParamsFilterFilm,
                categoriesFilms = currentCategory,
                year = calendar.get(Calendar.YEAR),
                month = (calendar.get(Calendar.MONTH) + 1).converterInMonth(),
                getPremierFilmUseCase, getTopFilmsUseCase, getFilmListUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    fun getFilmsByCategories(
        year: Int = calendar.get(Calendar.YEAR),
        month: String = (calendar.get(Calendar.MONTH) + 1).converterInMonth()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCategoryState.value = StateLoading.Loading
                val list = listOf(
                    HomeList(
                        category = CategoriesFilms.BEST,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.BEST),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.PREMIERS,
                        filmList = getPremierFilmUseCase.executePremieres(
                            year = year,
                            month = month
                        ).prepareToShow(20)
                    ),
                    HomeList(
                        category = CategoriesFilms.AWAIT,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.AWAIT),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.POPULAR,
                        filmList = getTopFilmsUseCase.executeTopFilms(
                            topType = TOP_TYPES.getValue(CategoriesFilms.POPULAR),
                            page = 1
                        )
                    ),
                    HomeList(
                        category = CategoriesFilms.TV_SERIES,
                        filmList = getFilmListUseCase.executeFilmsByFilter(
                            filters = ParamsFilterFilm(
                                type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES),
                                ratingFrom = 6
                            ),
                            page = 1
                        )
                    )
                )
                _homePageList.value = list
                _loadCategoryState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadCategoryState.value = StateLoading.Error(e.message.toString())
            }
        }
    }

    // FragmentAllFilms
    private var allFilmAdapter: AllFilmAdapter = AllFilmAdapter { }

    private lateinit var currentCategory: CategoriesFilms
    fun setCurrentCategory(category: CategoriesFilms) {
        currentCategory = category
        if (allFilmAdapter.itemCount != 0) allFilmAdapter.refresh()
    }

    fun getCurrentCategory() = currentCategory

    fun getAllFilmAdapter() = allFilmAdapter
    fun setAllFilmAdapter(adapter: AllFilmAdapter) {
        allFilmAdapter = adapter
    }

    val allSeries: Flow<PagingData<HomeItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            FilmsByFilterPagingSource(
                filters = ParamsFilterFilm(type = TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)),
                getFilmListUseCase = getFilmListUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

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
            Log.d(
                TAG,
                "CVM-235: galleryByType: ${currentParamsFilterGallery.filmId} | ${currentParamsFilterGallery.galleryType}"
            )
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
    private val _currentStaff = MutableStateFlow<ResponseStaffById?>(null)
    val currentStaff = _currentStaff.asStateFlow()

    private val _loadCurrentStaff = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCurrentStaff = _loadCurrentStaff.asStateFlow()

    fun getStaffDetail(staffId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCurrentStaff.value = StateLoading.Loading
                val staff = getStaffByIdUseCase.executeStaffById(staffId)
                _currentStaff.emit(staff)
                _loadCurrentStaff.value = StateLoading.Success
            } catch (e: Exception) {
                _loadCurrentStaff.value = StateLoading.Error(e.message.toString())
            }
        }
    }

    companion object {
        private val calendar = Calendar.getInstance()

        private var currentParamsFilterFilm = ParamsFilterFilm(
            countries = emptyMap(),
            genres = emptyMap(),
            order = "RATING",
            type = "",
            ratingFrom = 0,
            ratingTo = 10,
            yearFrom = 1000,
            yearTo = 3000,
            imdbId = null,
            keyword = ""
        )

        private var currentParamsFilterGallery = ParamsFilterGallery(
            filmId = 328,
            galleryType = "STILL"
        )

        data class HomeList(
            val category: CategoriesFilms,
            val filmList: List<HomeItem>
        )
    }
}