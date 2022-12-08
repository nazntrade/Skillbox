package ru.zhdanon.skillcinema.ui.filmsbyfilter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.zhdanon.skillcinema.data.ParamsFilterFilm
import ru.zhdanon.skillcinema.domain.GetFilmListUseCase
import ru.zhdanon.skillcinema.entity.HomeItem

class FilmsByFilterPagingSource(
    private val filters: ParamsFilterFilm,
    private val getFilmListUseCase: GetFilmListUseCase
) : PagingSource<Int, HomeItem>() {
    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)

        val response = getFilmListUseCase.executeFilmsByFilterCount(filters, page)
        return if (response.total != 0) {
            val items = response.items
            val nextKey = if (items.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(items, prevKey, nextKey)
        } else {
            LoadResult.Error(Exception())
        }
    }
}