package com.rkbapps.pixy.collectiondetails.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rkbapps.pixy.R
import com.rkbapps.pixy.collectiondetails.viewmodel.CollectionViewModel
import com.rkbapps.pixy.navigation.NavigationRoute
import com.rkbapps.pixy.topicdetails.screen.ImageItem


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionDetailsScreen(navController: NavHostController) {
    val viewModel: CollectionViewModel = hiltViewModel()
    val photos = viewModel.photoList.collectAsLazyPagingItems()
    val title = viewModel.title.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title.value,
                fontFamily = FontFamily(Font(resId = R.font.softers_regular)),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                textAlign = TextAlign.Center
            )
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(photos.itemCount) { index ->
                photos[index]?.let {
                    ImageItem(photosItem = it) {
                        navController.navigate(NavigationRoute.ImageDetails.route + "/${it.id}")
                    }
                }
            }
        }
    }
}