package com.rkbapps.pixy.imagedetails.repository


import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.db.ImageDao
import com.rkbapps.pixy.db.ImageEntity
import com.rkbapps.pixy.imagedetails.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val api: UnsplashAPI,
    private val imageDao: ImageDao,
) {

    private val _photo = MutableStateFlow(Photo())

    //private val _photo = MutableLiveData<Photo>()
    val photo: StateFlow<Photo>
        get() = _photo

    private val _isFav = MutableStateFlow(false)

    val isFav: StateFlow<Boolean>
        get() = _isFav

    suspend fun loadAPhoto(id: String) {
        val response = api.getAPhoto(id = id)
        if (response.isSuccessful && response.body() != null)
            _photo.emit(response.body()!!)
        //return _photo.emit(response.body()!!)
    }

    suspend fun addToFav(photo: Photo) {
        val imageEntity = ImageEntity(
            id = photo.id,
            slug = photo.slug,
            rawUrl = photo.urls.raw,
            fullUrl = photo.urls.full,
            regularUrl = photo.urls.regular,
            thumbUrl = photo.urls.thumb,
            smallUrl = photo.urls.small_s3,
            blurHash = photo.blur_hash,
            htmlLink = photo.links.html,
            likes = photo.likes,
            downloads = photo.downloads,
        )
        imageDao.addNewImage(imageEntity)
    }

    suspend fun checkIsFavorite(id: String) {
        _isFav.emit(imageDao.isExists(id))
    }

    suspend fun removeFromFav(id: String) {
        imageDao.deleteImageById(id)
    }

    suspend fun changeIsFav() {
        _isFav.emit(!_isFav.value)
    }


}