package com.rkbapps.pixy.topicdetails.screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.rkbapps.pixy.R
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.navigation.NavigationRoute
import com.rkbapps.pixy.topicdetails.viewmodel.TopicViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicDetailsScreen(navController: NavHostController) {
    val viewModel: TopicViewModel = hiltViewModel()
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
                modifier = Modifier.padding(5.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageItem(photosItem: Photo, onClick: () -> Unit) {
    val context = LocalContext.current
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
            contentDescription = photosItem.alt_description,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.placeholder)
        )
    }

}