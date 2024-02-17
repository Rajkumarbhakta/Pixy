package com.rkbapps.pixy.imagedetails.repository


import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.db.ImageDao
import com.rkbapps.pixy.db.ImageEntity
import com.rkbapps.pixy.imagedetails.models.DownloadLink
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.utils.ApiData
import com.rkbapps.pixy.utils.downloadService
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


    private val _downloadStatus: MutableState<String> = mutableStateOf("")
    val downloadStatus: State<String> = _downloadStatus
    fun trackDownload(context: Context, photo: Photo) {
        _downloadStatus.value = "Loading.."
        val queue = Volley.newRequestQueue(context)
        val stringRequest =object :StringRequest(
            Request.Method.GET,
            photo.links.download_location,
            { response ->
                //Log.d("DOWNLOAD_RESPONSE",response.toString())
                try {
                    val gson = Gson()
                    val downloadUrl = gson.fromJson(response.toString(), DownloadLink::class.java)
                    context.downloadService(
                        photo.alt_description ?: "pixy image",
                        downloadUrl.url
                    )
                    _downloadStatus.value = "Download started."
                } catch (e: Exception) {
                    _downloadStatus.value = "Error : ${e.localizedMessage}"
                }
            },
            {
                _downloadStatus.value = "Error : ${it.localizedMessage}"
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf<String,String>(
                    "Authorization" to "Client-ID ${ApiData.ACCESS_KEY}"
                )
            }
        }

        queue.add(stringRequest)
    }


}