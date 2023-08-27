package com.rkbapps.pixy.imagedetails.repository


import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.imagedetails.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(private val api: UnsplashAPI) {

    private val _photo = MutableStateFlow(Photo())

    //private val _photo = MutableLiveData<Photo>()
    val photo: StateFlow<Photo>
        get() = _photo

    suspend fun loadAPhoto(id: String) {
        val response = api.getAPhoto(id = id)
        if (response.isSuccessful && response.body() != null)
            _photo.emit(response.body()!!)
        //return _photo.emit(response.body()!!)
    }


}