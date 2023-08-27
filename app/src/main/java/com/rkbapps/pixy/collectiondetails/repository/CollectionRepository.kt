package com.rkbapps.pixy.collectiondetails.repository

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.imagedetails.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val api: UnsplashAPI,
    private val savedStateHandle: SavedStateHandle,
) {
    private val _title =
        MutableStateFlow<String>(savedStateHandle.get<String>("collection_title") ?: "Collection")

    val title: StateFlow<String>
        get() = _title

    var called = 0


    suspend fun loadPhotos(position: Int): List<Photo> {
        val id = savedStateHandle.get<String>("collection_id")
        //val response = api.getCollectionPhotos(id = id!!, page = position)
        called++
        Log.d("collection_photos", called.toString())
        return emptyList()//response.body()!!
    }


}