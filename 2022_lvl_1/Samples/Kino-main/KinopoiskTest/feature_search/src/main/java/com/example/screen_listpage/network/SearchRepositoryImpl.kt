package com.example.screen_listpage.network

import com.example.core.tools.SetSearch

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) {
    suspend fun getSearchFilms(setSearch: SetSearch, keyWord: String, page: Int) =
        searchApi.getSearchFromKeyword(
            setSearch.counterID,
            setSearch.genreId,
            setSearch.order.name,
            setSearch.type.name,
            setSearch.ratingFrom,
            setSearch.ratingTo,
            setSearch.yearFrom,
            setSearch.yearTo,
            keyWord,
            page
        )
}