package com.example.screen_listpage.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.screen_listpage.data.SearchFilmDTO

class SearchPagingRepo( private val networkRepository: SearchRepositoryImpl) {

    fun getFilms(keyWord:String): Pager<Int, SearchFilmDTO> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(networkRepository,keyWord) }
        )
    }
}