package com.becker.beckerSkillCinema.presentation.filmDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.GALLERY_TYPES
import com.becker.beckerSkillCinema.data.ParamsFilterGallery
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.data.seasons.Season
import com.becker.beckerSkillCinema.data.similarFilm.SimilarItem
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.domain.*
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.filmDetail.gallery.recyclerAdapter.GalleryFullPagingSource
import com.becker.beckerSkillCinema.utils.toLimitImages
import com.becker.beckerSkillCinema.utils.toLimitSimilarFilm
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val getActorsByFilmIdUseCase: GetActorsListUseCase,
    private val getGalleryByIdUseCase: GetGalleryByIdUseCase,
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {

    private val repository = CinemaRepository()

    private var _currentFilmId: Int? = null
    val currentFilmId
        get() = _currentFilmId

    var currentParamsFilterGallery =
        ParamsFilterGallery()

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

    private var _countSimilarFilm = MutableStateFlow(0)
    val countSimilarFilm = _countSimilarFilm.asStateFlow()

    private val _loadingCurrentFilmState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadingCurrentFilmState = _loadingCurrentFilmState.asStateFlow()

    fun getFilmById() {
        updateParamsFilterGallery()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingCurrentFilmState.value = StateLoading.Loading
                //mainInfoAboutMovie
                _currentFilm.value = getFilmByIdUseCase.executeFilmById(currentFilmId!!)
                setCrew()
                setImage()
                setSimilar()
                _loadingCurrentFilmState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadingCurrentFilmState.value = StateLoading.Error(e.message.toString())
                Timber.e("getFilmById $e")
            }
        }
    }

    private fun setCrew() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmCrewNotSorted = getActorsByFilmIdUseCase.executeActorsList(currentFilmId!!)
                toSortFilmCrew(filmCrewNotSorted)
            } catch (e: Throwable) {
                Timber.e("setCrew $e")
            }
        }
    }

    private fun setImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentFilmGallery.value = setGallery(currentFilmId!!).toLimitImages(20)
            } catch (e: Throwable) {
                Timber.e("setImage $e")
            }
        }
    }

    private fun setSimilar() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseSimilar = getSimilarFilmsUseCase.executeSimilarFilms(currentFilmId!!)
                if (responseSimilar.total != 0) {
                    val tempSimilarItem = responseSimilar.items!!
                    _currentFilmSimilar.value = tempSimilarItem.toLimitSimilarFilm(20)
                    _countSimilarFilm.value = tempSimilarItem.size
                } else {
                    Timber.e("responseSimilar = 0")
                }
            } catch (e: Throwable) {
                Timber.e("setSimilar $e")
            }
        }
    }

    fun getSeasons(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _seasons.value = getSeasonsUseCase.executeSeasons(seriesId).items
            } catch (e: Throwable) {
                Timber.e("getSeasons $e")
            }
        }
    }

    private fun toSortFilmCrew(filmCrewNotSorted: List<ResponseStaffByFilmId>) {
        val actors = emptyList<ResponseStaffByFilmId>().toMutableList()
        val makers = emptyList<ResponseStaffByFilmId>().toMutableList()
        filmCrewNotSorted.forEach { thisPeople ->
            if (thisPeople.professionKey == "ACTOR") actors.add(thisPeople)
            else makers.add(thisPeople)
        }
        _currentFilmActors.value = actors
        _currentFilmMakers.value = makers
    }

    // For FragmentGallery
    private val _totalNumberOfPictures = MutableStateFlow(0)
    val totalNumberOfPictures = _totalNumberOfPictures.asStateFlow()

    private val _numberOfPicturesByCategory = MutableStateFlow<Map<String, Int>>(emptyMap())
    val numberOfPicturesByCategory =
        _numberOfPicturesByCategory.asStateFlow()  //it keeps countImagesByCategory for screenDetailImage

    val galleryByType: Flow<PagingData<ItemImageGallery>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            GalleryFullPagingSource(
                getGalleryByIdUseCase = getGalleryByIdUseCase,
                filterParams = currentParamsFilterGallery
            )
        }
    ).flow.cachedIn(viewModelScope)

    private suspend fun setGallery(filmId: Int): List<ItemImageGallery> {
        _totalNumberOfPictures.value = 0
        val tempNumberOfPicturesByCategory = mutableMapOf<String, Int>()
        var tempTotalNumberOfPictures = 0
        val totalPictures = mutableListOf<ItemImageGallery>()
        viewModelScope.async(Dispatchers.IO) {
            try {
                GALLERY_TYPES.forEach {
                    val tempPicturesByCategory = getGalleryByIdUseCase
                        .executeGalleryByFilmId(filmId, it.key, 1)
                    tempNumberOfPicturesByCategory[it.key] = tempPicturesByCategory.total
                    tempTotalNumberOfPictures += tempPicturesByCategory.total
                    totalPictures += tempPicturesByCategory.items
                }
                _totalNumberOfPictures.value = tempTotalNumberOfPictures
                _numberOfPicturesByCategory.value = tempNumberOfPicturesByCategory
            } catch (e: Throwable) {
                Timber.e("setGallery $e")
            }
        }.await()
        return totalPictures
    }

    // Update the gallery with new FilmId
    fun updateParamsFilterGallery(
        filmId: Int? = currentFilmId,
        galleryType: String = "STILL"
    ) {
        val newParamsFilterGallery = ParamsFilterGallery(
            filmId = filmId!!,
            galleryType = galleryType
        )
        currentParamsFilterGallery = newParamsFilterGallery
    }

    fun putFilmId(filmId: Int) {
        repository.putFilmId(filmId)
    }

    fun getFilmId() {
        val filmId = repository.getCurrentFilmId()
        if (filmId != null) {
            if (_currentFilmId != filmId) {
                _currentFilmId =
                    filmId
            }
        }
    }
}