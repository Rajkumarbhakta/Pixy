package com.rkbapps.pixy.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.rkbapps.pixy.R
import com.rkbapps.pixy.home.models.PhotosItem
import com.rkbapps.pixy.home.viewmodel.HomeViewModel
import com.rkbapps.pixy.navigation.NavigationRoute
import com.rkbapps.pixy.screens.LoadingError
import com.rkbapps.pixy.screens.NoInternet
import com.rkbapps.pixy.utils.checkNetwork


@Composable
fun HomeScreen(navController: NavController) {
    if (LocalContext.current.checkNetwork()) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val photos: LazyPagingItems<PhotosItem> = homeViewModel.photoList.collectAsLazyPagingItems()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
        ) {

            items(photos.itemCount) { index ->
                photos[index]?.let {
                    ImageItem(photosItem = it) {
                        navController.navigate(NavigationRoute.ImageDetails.route + "/${it.id}")
                    }
                }
            }

            when (photos.loadState.append) {
                is LoadState.Loading -> {
                    item {
//                        Box(
//                            contentAlignment = Alignment.Center,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp, horizontal = 16.dp)
//                        ) {
//                            CircularProgressIndicator()
//                        }
                    }
                }

                is LoadState.NotLoading -> Unit
                is LoadState.Error -> {



                }
            }
        }

        when (photos.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.NotLoading -> Unit
            is LoadState.Error -> {
            LoadingError()
            }
        }


    } else {
        NoInternet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageItem(photosItem: PhotosItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.LightGray),
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp)), elevation = CardDefaults.cardElevation(8.dp),
        onClick = {
            onClick()
        }
    ) {
        AsyncImage(
            model = photosItem.urls.regular,
            contentDescription = photosItem.description,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.placeholder)
        )
    }

}