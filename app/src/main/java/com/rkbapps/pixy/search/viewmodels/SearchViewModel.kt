package com.rkbapps.pixy.search.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.search.model.SearchResult
import com.rkbapps.pixy.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val searchPhotoList: StateFlow<SearchResult>
        get() = repository.searchPhotoList

    val _searchPhotos = MutableStateFlow<PagingData<Photo>>(PagingData.empty())

    val searchPhotos: StateFlow<PagingData<Photo>>
        get() = _searchPhotos


    fun search(query: String) {
        viewModelScope.launch {
            repository.getSearchResult(query)
        }
    }

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            repository.search(query).cachedIn(viewModelScope).collect {
                _searchPhotos.emit(it)
            }
        }
    }

}