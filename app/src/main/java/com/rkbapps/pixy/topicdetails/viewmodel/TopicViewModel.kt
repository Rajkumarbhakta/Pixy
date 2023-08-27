package com.rkbapps.pixy.topicdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rkbapps.pixy.topicdetails.paging.TopicPagingSource
import com.rkbapps.pixy.topicdetails.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class TopicViewModel @Inject constructor(private val repository: TopicRepository) : ViewModel() {

    val title: StateFlow<String>
        get() = repository.title

    val photoList = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100)
    ) {
        TopicPagingSource(repository)
    }.flow.cachedIn(viewModelScope)


}