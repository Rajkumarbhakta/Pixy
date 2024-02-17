package com.rkbapps.pixy.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rkbapps.pixy.home.models.PhotosItem
import com.rkbapps.pixy.home.repository.HomeRepository
import javax.inject.Inject

class HomePagingSource @Inject constructor(private val repository: HomeRepository) :
    PagingSource<Int, PhotosItem>() {
    override fun getRefreshKey(state: PagingState<Int, PhotosItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosItem> {
        return try {
            val position = params.key ?: 1
            val response = repository.loadPhotos(position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}