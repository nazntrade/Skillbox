package com.becker.beckerSkillCinema.presentation.filmDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.GALLERY_TYPES
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.data.seasons.Season
import com.becker.beckerSkillCinema.data.similarFilm.SimilarItem
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.domain.*
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.gallery.recyclerAdapter.GalleryFullPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val getActorsByFilmIdUseCase: GetActorsListUseCase,
    private val getGalleryByIdUseCase: GetGalleryByIdUseCase,
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {

    // FragmentFilmDetail

    private val repository = CinemaRepository()

    private var _localFilmId: Int? = null
    val localFilmId
        get() = _localFilmId


    init {
        getFilmId()
        getFilmById()
    }


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

    fun getFilmById() {
        updateParamsFilterGallery()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCurrentFilmState.value = StateLoading.Loading
                // film
                val tempFilm = getFilmByIdUseCase.executeFilmById(localFilmId!!)
                _currentFilm.value = tempFilm
                // staffs
                val tempActorList = getActorsByFilmIdUseCase.executeActorsList(localFilmId!!)
                sortingActorsAndMakers(tempActorList)
                // gallery
                setGalleryCount(localFilmId!!)
                _currentFilmGallery.value =
                    getGalleryByIdUseCase.executeGalleryByFilmId(
                        HomeViewModel.currentParamsFilterGallery.filmId,
                        HomeViewModel.currentParamsFilterGallery.galleryType,
                        1
                    ).items
                // similar
                val responseSimilar = getSimilarFilmsUseCase.executeSimilarFilms(localFilmId!!)
                if (responseSimilar.total != 0) {
                    _currentFilmSimilar.value = responseSimilar.items!!
                }
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
                filterParams = HomeViewModel.currentParamsFilterGallery
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

    private fun updateParamsFilterGallery(filmId: Int = localFilmId!!, galleryType: String = "STILL") {
        HomeViewModel.currentParamsFilterGallery =
            HomeViewModel.currentParamsFilterGallery.copy(
                filmId = filmId,
                galleryType = galleryType
            )
    }

    fun putFilmId(filmId: Int) {
        repository.putFilmId(filmId)
    }

    fun getFilmId() {
        val filmId = repository.getCurrentFilmId()
        if (filmId != null) {
            if (_localFilmId != filmId) {
                _localFilmId =
                    filmId
            }
        }
    }
}