package com.example.presentation_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.tools.ID_HISTORY_COLLECTION
import com.example.core.tools.ID_LOOK_COLLECTION
import com.example.core.tools.NAVIGATE__TO_INFO_FILM
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.DetailsCategory
import com.example.feature_database.entity.FilmEntity
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.toNestedCollection
import com.example.feature_database.toNestedFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val database: CollectionsFilmsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var listLikeFilms = listOf<FilmEntity?>()
    private var listFavorite = listOf<FilmEntity?>()
    private val profileList = MutableSharedFlow<List<BaseCategory>>()
    val profile = profileList.asSharedFlow()


    fun getCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            getListCollections()
            database.getAllCollection().onEach {
                profileList.emit(
                    listOf(
                        DetailsCategory(
                            CategoryInfo.LOOK,
                            listLikeFilms.toNestedFilms()
                        ),
                        DetailsCategory(
                            CategoryInfo.COLLECTION,
                            it.toNestedCollection()
                        ),
                        DetailsCategory(
                            CategoryInfo.HISTORY,
                            listFavorite.toNestedFilms()
                        )
                    )
                )
            }.launchIn(viewModelScope)
        }
    }


    private fun getListCollections() {
        var list = database.getFilmsFromCollectionID(ID_LOOK_COLLECTION)
        listLikeFilms = database.getFilmsFromListID(list)

        list = database.getFilmsFromCollectionID(ID_HISTORY_COLLECTION)
        listFavorite = database.getFilmsFromListID(list)

    }

    fun navigateToCategory(pageCategory: BaseCategory, navigateID: String) {
        if (pageCategory is PageCategory)
            savedStateHandle[navigateID] =
                PageCategory(pageCategory.category, emptyList(), pageCategory.query)
        else savedStateHandle[navigateID] =
            PageCategory(pageCategory.category, emptyList())
    }

    fun navigateToInfoFilms(id: Int?) {
        savedStateHandle[NAVIGATE__TO_INFO_FILM] = id
    }

    fun addCollection(nameCollection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.addCollection(nameCollection)
        }

    }
}