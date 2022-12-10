package com.example.homepage.presentation.homepage.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.adapterdelegate.data.createStaffList
import com.example.core.tools.ID_HISTORY_COLLECTION
import com.example.core.tools.ID_LOOK_COLLECTION
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.StartCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.category.DetailsCategory
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.repository.DataBaseRepository
import com.example.feature_database.toNestedFilms
import com.example.homepage.presentation.homepage.domain.NetworkCategoryRepository
import com.example.homepage.presentation.homepage.tools.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListPagePagingSource(
    private val networkRepository: NetworkCategoryRepository,
    private val dataBaseRepository: DataBaseRepository,
    private val collectionDataBaseRepository: CollectionsFilmsRepository,
    private val viewModelScope: CoroutineScope,
    private val category: BaseCategory?,

    ) : PagingSource<Int, NestedInfoInCategory>() {

    override fun getRefreshKey(state: PagingState<Int, NestedInfoInCategory>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NestedInfoInCategory> {

        val page = params.key ?: FIRST_PAGE
        val pageSize = params.loadSize

        return kotlin.runCatching {
            withContext(Dispatchers.IO) {
                getFilms(page, category)
            }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (it.size < pageSize) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )

    }

    private suspend fun getFilms(page: Int, category: BaseCategory?): List<NestedInfoInCategory> {
        category as StartCategory
        return when (category.category) {

            CategoryInfo.POPULAR -> loadPopular(networkRepository, page).mergeDatabase(dataBaseRepository, viewModelScope)
            CategoryInfo.PREMIERS -> loadPremieres(
                networkRepository, category.query!!.year, category.query!!.month
            ).mergeDatabase(dataBaseRepository, viewModelScope)
            CategoryInfo.BEST -> loadBest(networkRepository, page)
            CategoryInfo.RANDOM -> loadFilmsByCounterAdnGenre(
                networkRepository, page, category.query!!.counterID, category.query!!.genreId
            ).mergeDatabase(dataBaseRepository, viewModelScope)
            CategoryInfo.SIMILAR -> networkRepository.getSimilarFilms(category.query?.id!!).items.toListBaseFilms().mergeDatabase(dataBaseRepository, viewModelScope)
            CategoryInfo.STAFF -> {
               val staffList = networkRepository.getStaffByFilmsId(category.query?.id!!)
                    .createStaffList().first() as DetailsCategory
                staffList.listValue
            }
            CategoryInfo.ACTOR ->  {
                val actorList = networkRepository.getStaffByFilmsId(category.query?.id!!)
                    .createStaffList().last() as DetailsCategory
                actorList.listValue
            }
            CategoryInfo.COLLECTION->{
                val dataList = collectionDataBaseRepository.getFilmsFromCollectionID(category.query?.id!!)
                collectionDataBaseRepository.getFilmsFromListID(dataList).toNestedFilms()
            }
            CategoryInfo.HISTORY->{
                val dataList = collectionDataBaseRepository.getFilmsFromCollectionID(
                    ID_HISTORY_COLLECTION)
                collectionDataBaseRepository.getFilmsFromListID(dataList).toNestedFilms()
            }
            CategoryInfo.LOOK->{
                val dataList = collectionDataBaseRepository.getFilmsFromCollectionID(
                    ID_LOOK_COLLECTION)
                collectionDataBaseRepository.getFilmsFromListID(dataList).toNestedFilms()
            }
            CategoryInfo.TV_SERIES->{
                loadSerials(networkRepository,page)
            }
            else -> emptyList()
        }
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}