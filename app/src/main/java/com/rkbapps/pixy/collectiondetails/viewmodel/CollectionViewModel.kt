package com.rkbapps.pixy.collectiondetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rkbapps.pixy.collectiondetails.paging.CollectionPagingSource
import com.rkbapps.pixy.collectiondetails.repository.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val repository: CollectionRepository) :
    ViewModel() {

    val title: StateFlow<String>
        get() = repository.title

    val photoList = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100)
    ) {
        CollectionPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

}