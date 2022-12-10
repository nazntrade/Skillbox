package com.example.homepage.presentation.homepage.domain.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.category.BaseCategory
import com.example.feature_database.repository.CollectionsFilmsRepository
import com.example.feature_database.repository.DataBaseRepository
import com.example.homepage.presentation.homepage.domain.NetworkCategoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PagingRepository(
    private val networkRepository: NetworkCategoryImpl,
    private val dataBaseRepository: DataBaseRepository,
    private val collectionsFilmsRepository: CollectionsFilmsRepository,

) {
    fun getFlowFilms(category: BaseCategory?, viewModelScope: CoroutineScope): Flow<PagingData<NestedInfoInCategory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ListPagePagingSource(networkRepository,
                dataBaseRepository,collectionsFilmsRepository,viewModelScope, category) }
        ).flow
    }
}