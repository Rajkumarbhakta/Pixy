package com.rkbapps.pixy.fav.repository

import com.rkbapps.pixy.db.ImageDao
import com.rkbapps.pixy.db.ImageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

class FavScreenRepository @Inject constructor(private val imageDao: ImageDao) {

    private val _favImages = MutableStateFlow<List<ImageEntity>>(emptyList())

    val favImages: StateFlow<List<ImageEntity>>
        get() = _favImages

    suspend fun getAllFavImages() {
        val images = imageDao.getAllImages()
        _favImages.emitAll(images)
    }
}