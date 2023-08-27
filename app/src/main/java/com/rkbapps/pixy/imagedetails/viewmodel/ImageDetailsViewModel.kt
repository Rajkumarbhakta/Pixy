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


    init {
        viewModelScope.launch {
            val id = saveStateHandel.get<String>("id")
            repository.loadAPhoto(id ?: "")
        }
    }
}