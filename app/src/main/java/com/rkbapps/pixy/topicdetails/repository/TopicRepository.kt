package com.rkbapps.pixy.topicdetails.repository

import androidx.lifecycle.SavedStateHandle
import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.imagedetails.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val api: UnsplashAPI,
    private val savedStateHandle: SavedStateHandle,
) {

    private val _title = MutableStateFlow(savedStateHandle.get<String>("topic_title") ?: "Topic")

    val title: StateFlow<String>
        get() = _title


    suspend fun loadPhotos(page: Int): List<Photo> {
        val id = savedStateHandle.get<String>("topic_id")
        val response = api.getTopicPhotos(id = id!!, page = page)
        return response.body()!!
    }
}