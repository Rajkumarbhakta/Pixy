package com.rkbapps.pixy.fav.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rkbapps.pixy.R
import com.rkbapps.pixy.db.ImageEntity
import com.rkbapps.pixy.fav.viewmodel.FavScreenViewModel
import com.rkbapps.pixy.navigation.NavigationRoute

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavScreen(navController: NavHostController) {
    val viewModel: FavScreenViewModel = hiltViewModel()
    val favImages = viewModel.favImages.collectAsState()
    if (favImages.value.isEmpty()) {
        Text(text = "No fav images", fontSize = 20.sp)
    } else {
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
            items(favImages.value) { image ->
                image.let {
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
fun ImageItem(photosItem: ImageEntity, onClick: () -> Unit) {
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
            model = photosItem.regularUrl,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.placeholder)
        )
    }

}