package com.example.feature_details.presentation.films_details.bottom_sheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.tools.base_model.FilmsCollection
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.toFilmsCollectionList
import com.example.feature_details.presentation.films_details.details.FilmInfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BottomSheetViewModel(
    private val database: CollectionsFilmsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val film: FilmEntity? = savedStateHandle[FilmInfoViewModel.BOTTOM_SHEET_FILMS]

    private val _collections =
        MutableSharedFlow<List<FilmsCollection>>()
    val collections = _collections.asSharedFlow()

    fun getFilm() = film!!

    fun getCollectionFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            database.getAllCollection().collect {
                val activeCollection =
                    film?.let { film -> database.getCollectionFromFilmId(film.filmId) }
                _collections.emit(it.toFilmsCollectionList(activeCollection))
            }
        }
    }

    fun addCollection(nameCollection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.addCollection(nameCollection)
        }
    }

    fun addFilmsInCollection(collection: FilmsCollection, isSelect: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            if (film != null)
                if (collection.isCheck)
                    database.insertFromBottomShit(film, collection.id!!, isSelect)
                else database.deleteFilmInCollectionFromBottomShit(collection.id!!, film,isSelect)
        }
    }

}