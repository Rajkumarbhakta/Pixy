package com.rkbapps.pixy.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rkbapps.pixy.home.models.PhotosItem
import com.rkbapps.pixy.home.paging.HomePagingSource
import com.rkbapps.pixy.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val photos: StateFlow<List<PhotosItem>>
        get() = repository.photos

    init {
        viewModelScope.launch {
            //repository.loadPhotos()
        }
    }

    val photoList = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100)
    ) {
        HomePagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    //flow.cachedIn(viewModelScope)

        //.liveData.cachedIn(viewModelScope)





}