package com.rkbapps.pixy.home.repository

import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.home.models.PhotosItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class HomeRepository @Inject constructor(private val unsplashAPI: UnsplashAPI) {

    private val _photos = MutableStateFlow<List<PhotosItem>>(emptyList())

    val photos: StateFlow<List<PhotosItem>>
        get() = _photos

    suspend fun loadPhotos() {
        val response = unsplashAPI.getPhotoList()
        if (response.isSuccessful && response.body() != null) {
            _photos.emit(response.body()!!)
        }
    }

    suspend fun loadPhotos(page: Int): List<PhotosItem> {
        val response = unsplashAPI.getPhotoList(page = page)
        return response.body() ?: emptyList()
    }

}