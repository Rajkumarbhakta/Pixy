package com.rkbapps.pixy.collectiondetails.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rkbapps.pixy.collectiondetails.repository.CollectionRepository
import com.rkbapps.pixy.imagedetails.models.Photo
import javax.inject.Inject

class CollectionPagingSource @Inject constructor(private val repository: CollectionRepository) :
    PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
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