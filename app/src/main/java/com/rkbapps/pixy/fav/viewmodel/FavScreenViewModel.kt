package com.rkbapps.pixy.fav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkbapps.pixy.db.ImageEntity
import com.rkbapps.pixy.fav.repository.FavScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavScreenViewModel @Inject constructor(private val repository: FavScreenRepository) :
    ViewModel() {

    val favImages: StateFlow<List<ImageEntity>>
        get() = repository.favImages

    init {
        viewModelScope.launch {
            repository.getAllFavImages()
        }
    }
}