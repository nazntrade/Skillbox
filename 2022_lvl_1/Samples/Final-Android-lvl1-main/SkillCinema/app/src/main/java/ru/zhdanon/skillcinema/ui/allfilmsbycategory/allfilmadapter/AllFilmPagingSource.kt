package ru.zhdanon.skillcinema.ui.allfilmsbycategory.allfilmadapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.zhdanon.skillcinema.data.CategoriesFilms
import ru.zhdanon.skillcinema.data.ParamsFilterFilm
import ru.zhdanon.skillcinema.data.TOP_TYPES
import ru.zhdanon.skillcinema.domain.GetFilmListUseCase
import ru.zhdanon.skillcinema.domain.GetPremierFilmUseCase
import ru.zhdanon.skillcinema.domain.GetTopFilmsUseCase
import ru.zhdanon.skillcinema.entity.HomeItem

class AllFilmPagingSource(
    private val filterParams: ParamsFilterFilm,
    private val categoriesFilms: CategoriesFilms,
    private val year: Int,
    private val month: String,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getFilmListUseCase: GetFilmListUseCase
) : PagingSource<Int, HomeItem>() {
    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            when (categoriesFilms) {
                CategoriesFilms.PREMIERS -> {
                    getPremierFilmUseCase.executePremieres(year, month)
                }
                CategoriesFilms.TV_SERIES -> {
                    getFilmListUseCase
                        .executeFilmsByFilter(
                            filters = filterParams,
                            page = page
                        )
                }
                else -> {
                    getTopFilmsUseCase.executeTopFilms(
                        topType = TOP_TYPES.getValue(categoriesFilms),
                        page = page
                    )
                }
            }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}