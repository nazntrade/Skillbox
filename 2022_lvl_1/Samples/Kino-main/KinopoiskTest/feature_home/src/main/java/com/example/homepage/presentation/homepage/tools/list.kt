package com.example.homepage.presentation.homepage.tools

import com.example.adapterdelegate.data.SimilarFilmsDTO
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.base_model.category.PageCategory
import com.example.feature_database.repository.DataBaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun CategoryInfo.createCategory(list: List<NestedInfoInCategory>, query: Query? = null) =
    PageCategory(this, list, query)

fun List<SimilarFilmsDTO>.toListBaseFilms(): MutableList<BaseFilm> {
    val list = mutableListOf<BaseFilm>()
    this.forEach {
        list.add(BaseFilm(it.nameRu,it.posterUrlPreview, filmId = it.filmId))
    }
    return list
}

fun List<BaseFilm>.mergeDatabase(dataBaseRepository: DataBaseRepository, viewModelScope: CoroutineScope): List<BaseFilm> {
    val listFilmsId = mutableListOf<Int>()
    this.forEach { film -> listFilmsId.add(film.filmId) }
    dataBaseRepository.getStatusFilmList(listFilmsId) .onEach { map->
        this.forEach { film ->
            film.isLook = map[film.filmId] ?: false
        }
    }.launchIn(viewModelScope)

    return this
}

fun List<PageCategory>.mergeHomeDatabase(
    dataBaseRepository: DataBaseRepository,
    _homePageList: MutableStateFlow<List<BaseCategory>>,
    viewModelScope: CoroutineScope
) {

    val listFilmsId = mutableListOf<Int>()
    this.forEach { category->
        category.list.forEach { film ->
            if (film is BaseFilm)
                listFilmsId.add(film.filmId) }
    }
    dataBaseRepository.getStatusFilmList(listFilmsId).onEach { mapData ->
        this.map { category ->
            category.list.forEach { film ->
                if (film is BaseFilm)
                    film .isLook = mapData[film.filmId] ?: false
            }
        }
        _homePageList.value = this
    }.launchIn(viewModelScope)
}