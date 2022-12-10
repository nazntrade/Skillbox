package com.example.feature_details.presentation.films_details.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.adapterdelegate.data.createStaffList
import com.example.core.tools.*
import com.example.core.tools.all.LoadState
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.basefrahment.BaseViewModel
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.DetailsCategory
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.repository.FilmDataBaseRepository
import com.example.feature_details.data.ButtonPoster
import com.example.feature_details.data.ImageCategory
import com.example.feature_details.data.detailsFilm_page.dto.InfoFilmDTO
import com.example.feature_details.data.detailsFilm_page.model.PosterFilm
import com.example.feature_details.domain.repository_ipl.NetworkFilmDetailsRepositoryImpl
import com.example.feature_details.tools.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class FilmInfoViewModel(
    private val networkRepository: NetworkFilmDetailsRepositoryImpl,
    private val filmsDatabaseRepository: FilmDataBaseRepository,
    private val collectionsFilmsRepository: CollectionsFilmsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val filmId: Int? = savedStateHandle[NAVIGATE__TO_INFO_FILM]

    private val _film = MutableSharedFlow<Pair<PosterFilm, List<BaseCategory>>>()
    val film = _film.asSharedFlow()

    private val _filmStatus = MutableSharedFlow<FilmEntity>()
    val filmStatus = _filmStatus.asSharedFlow()

    private var filmEntity: FilmEntity? = null
    private var isEmptyInfoFromDataBase = true


    fun getFilmForID(id: Int? = filmId) =
        viewModelScope.launch(handler + Dispatchers.IO) {
            _loadState.value = LoadState.LOADING

            if (id != null) {
                val infoFilms = networkRepository.getFilmForID(id)
                filmsDatabaseRepository.getFilmByID(id).onEach {
                    filmEntity = it
                    if (it == null) {
                        collectionsFilmsRepository.insetFilm(infoFilms.toFilmEntity())
                        collectionsFilmsRepository
                            .insertFilmCollection(infoFilms.toFilmEntity(), ID_HISTORY_COLLECTION)

                    } else _filmStatus.emit(it)
                    _film.emit(
                        Pair(
                            infoFilms.toPosterFilms(),
                            createListCategoryInfo(id, infoFilms)
                        )
                    )
                }.launchIn(viewModelScope + Dispatchers.IO +handler)
            }
            _loadState.value = LoadState.SUCCESS
        }


    fun workDatabase(button: ButtonPoster) =
        viewModelScope.launch(Dispatchers.IO) {
            filmEntity?.let { film ->

                when (button) {
                    ButtonPoster.LIKE ->
                        film.isLike = addOrDeleteCollections(ID_LIKE_COLLECTION, film)

                    ButtonPoster.FAVORITE ->
                        film.isFavorite = addOrDeleteCollections(ID_FAVORITE_COLLECTION, film)

                    ButtonPoster.LOOK ->
                        film.isLook = addOrDeleteCollections(ID_LOOK_COLLECTION, film)

                }
                filmsDatabaseRepository.updateFilm(film)
            }
        }

    private fun addOrDeleteCollections(collectionId: Int, film: FilmEntity): Boolean {

        val setCollection = collectionsFilmsRepository
            .getCollectionFromFilmIdAndCollectionId(film.filmId, collectionId)

        return if (collectionId == setCollection) {
            collectionsFilmsRepository.deleteFilmInCollection(collectionId, film.filmId)
            false
        } else {
            collectionsFilmsRepository.insertFilmCollection(film, collectionId)
            true
        }

    }

    private suspend fun createListCategoryInfo(
        id: Int,
        infoFilms: InfoFilmDTO
    ): MutableList<BaseCategory> {
        val listInfo = mutableListOf<BaseCategory>()

        listInfo.add(infoFilms.toDescriptions())

        if (infoFilms.isSerial)
            listInfo.add(networkRepository.getSeasons(filmId!!).toSeasonsItem())

            listInfo.addAll(networkRepository.getAllStaffFilmsByID(id).createStaffList())

        listInfo.add(DetailsCategory(CategoryInfo.GALLERY, getGalleryList(id, GALLERY_SIZE)))

        listInfo.add(
            DetailsCategory(
                CategoryInfo.SIMILAR, networkRepository.getSimilarFilmsByID(id).toListBaseFilm()
            )
        )
        return listInfo
    }

    private suspend fun getGalleryList(
        id: Int,
        gallerySize: Int
    ): MutableList<NestedInfoInCategory> {
        val galleryList = mutableListOf<NestedInfoInCategory>()

        for (position in 0..ImageCategory.values().lastIndex) {
            if (galleryList.size < gallerySize) galleryList
                .addAll(
                    networkRepository.getGalleryFilmsByID(
                        id, ImageCategory.values()[position].name
                    ).items
                )
            else break
        }
        return galleryList
    }

    fun navigateToBottomSheet() {
        savedStateHandle[BOTTOM_SHEET_FILMS] = filmEntity
        savedStateHandle[IS_EMPTY_FILM_DATABASE] = isEmptyInfoFromDataBase
    }

    fun navigateToStaffInfo(categoryInfo: PageCategory) {
        savedStateHandle[NAVIGATE__TO_STAFF] = categoryInfo.query?.id
    }

    fun navigateToCategory(category: BaseCategory) {
        savedStateHandle[NAVIGATE__CATEGORY_PAGE] = PageCategory(
            category.category, emptyList(), Query(id = filmId)
        )
    }

    fun navigateToGalleryList() {
        savedStateHandle[NAVIGATE__TO_GALLERY_LIST] = filmId
    }

    fun navigateToSeasonsList(categoryInfo: PageCategory) {
        savedStateHandle[NAVIGATE__TO_SEASONS] = categoryInfo.list
    }

    companion object {
        private const val GALLERY_SIZE = 20
        const val BOTTOM_SHEET_FILMS = "bottom_sheet_films"
        const val IS_EMPTY_FILM_DATABASE = "item_in_data_base"
    }
}