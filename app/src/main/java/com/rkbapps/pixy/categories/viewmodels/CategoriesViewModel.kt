package com.rkbapps.pixy.categories.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkbapps.pixy.categories.models.Topic
import com.rkbapps.pixy.categories.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository: CategoriesRepository) :
    ViewModel() {

    val topics: StateFlow<List<Topic>>
        get() = repository.topics


    val collection: StateFlow<List<Topic>>
        get() = repository.collection


    init {
        viewModelScope.launch {
            repository.loadTopics()
            repository.loadCollection()
        }
    }
}