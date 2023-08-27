package com.rkbapps.pixy.categories.repository

import com.rkbapps.pixy.api.UnsplashAPI
import com.rkbapps.pixy.categories.models.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val api: UnsplashAPI) {

    private val _topics = MutableStateFlow<List<Topic>>(emptyList())

    val topics: StateFlow<List<Topic>>
        get() = _topics

    suspend fun loadTopics() {
        val response = api.getTopicList()
        if (response.isSuccessful && response.body() != null) {
            _topics.emit(response.body()!!)
        }
    }

    private val _collection = MutableStateFlow<List<Topic>>(emptyList())

    val collection: StateFlow<List<Topic>>
        get() = _collection

    suspend fun loadCollection() {
        val response = api.getCollection()
        if (response.isSuccessful && response.body() != null) {
            _collection.emit(response.body()!!)
        }
    }

}