package com.rkbapps.pixy.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.search.repository.SearchRepository

class SearchPagingSource(
    private val repository: SearchRepository,
    private val query: String,
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
//            ?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val position = params.key ?: 1
            val response = repository.getSearchResult(query = query, page = position)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}