package ru.zhdanon.skillcinema.ui.gallery.recycleradapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.zhdanon.skillcinema.data.ParamsFilterGallery
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery
import ru.zhdanon.skillcinema.domain.GetGalleryByIdUseCase

class GalleryFullPagingSource(
    private val getGalleryByIdUseCase: GetGalleryByIdUseCase,
    private val filterParams: ParamsFilterGallery
) : PagingSource<Int, ItemImageGallery>() {
    override fun getRefreshKey(state: PagingState<Int, ItemImageGallery>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemImageGallery> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            getGalleryByIdUseCase.executeGalleryByFilmId(
                filterParams.filmId,
                filterParams.galleryType,
                page
            ).items
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