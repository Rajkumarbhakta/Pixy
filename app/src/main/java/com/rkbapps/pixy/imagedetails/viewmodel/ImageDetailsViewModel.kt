package com.rkbapps.pixy.imagedetails.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.imagedetails.repository.ImageDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            val id = saveStateHandel.get<String>("id")
            repository.loadAPhoto(id ?: "")
            repository.checkIsFavorite(id ?: "")
        }
    }

    fun addToFav(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFav(photo)
        }
    }

    fun removeFromFav(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFav(id)
        }
    }

    fun changeIsFav() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeIsFav()
        }
    }

    val downloadStatus: State<String> = repository.downloadStatus

    fun trackDownload(context: Context, photo: Photo){
        repository.trackDownload(context, photo)
    }
}