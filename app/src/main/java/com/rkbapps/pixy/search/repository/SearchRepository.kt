package com.rkbapps.pixy.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.search.model.SearchResult
import com.rkbapps.pixy.search.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: UnsplashAPI) {

    private val _searchPhotoList = MutableStateFlow<SearchResult>(SearchResult())

    val searchPhotoList: StateFlow<SearchResult>
        get() = _searchPhotoList

    suspend fun getSearchResult(query: String) {
        val response = api.getSearchResult(query = query)
        if (response.isSuccessful) {
            _searchPhotoList.emit(response.body()!!)
        }
    }

    suspend fun getSearchResult(query: String, page: Int): SearchResult {
        val response = api.getSearchResult(query = query, page = page)
        return response.body() ?: SearchResult()
    }

    fun search(query: String): Flow<PagingData<Photo>> {
        val searchResult = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100)
        ) {
            SearchPagingSource(this, query)
        }.flow
        return searchResult;
    }
}