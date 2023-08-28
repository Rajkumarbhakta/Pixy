package com.rkbapps.pixy.imagedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.imagedetails.repository.ImageDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val repository: ImageDetailsRepository,
    private val saveStateHandel: SavedStateHandle,
) : ViewModel() {

    val photo: StateFlow<Photo>
        get() = repository.photo

    val isFav: StateFlow<Boolean>
        get() = repository.isFav


    init {
        viewModelScope.launch {
            val id = saveStateHandel.get<String>("id")
            repository.loadAPhoto(id ?: "")
            repository.checkIsFavorite(id ?: "")
        }
    }

    fun addToFav(photo: Photo) {
        viewModelScope.launch {
            repository.addToFav(photo)
        }
    }

    fun removeFromFav(id: String) {
        viewModelScope.launch {
            repository.removeFromFav(id)
        }
    }

    fun changeIsFav() {
        viewModelScope.launch {
            repository.changeIsFav()
        }
    }
}