package com.rkbapps.pixy.search.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rkbapps.pixy.R
import com.rkbapps.pixy.navigation.NavigationRoute
import com.rkbapps.pixy.search.viewmodels.SearchViewModel
import com.rkbapps.pixy.topicdetails.screen.ImageItem
import com.rkbapps.pixy.ui.theme.lightBlue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    val searchQuery = rememberSaveable {
        mutableStateOf("")
    }
    val focus = remember {
        mutableStateOf(true)
    }
    val viewModel: SearchViewModel = hiltViewModel()
    // val photoList = viewModel.searchPhotoList.collectAsState()
    val searchPhotos = viewModel.searchPhotos.collectAsLazyPagingItems()
    val composition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.not_found_animation))

    Scaffold(topBar = {
        SearchBar(searchQuery, focus) {
            if (it.isNotEmpty()) {
                //viewModel.search(it)
                viewModel.searchPhotos(it)
            }
        }
    }) { innerPadding ->
        if (searchPhotos.itemCount != 0) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.padding(innerPadding)
            ) {
//                items(photoList.value.results) {
//                    ImageItem(photosItem = it) {
//                        navController.navigate(NavigationRoute.ImageDetails.route + "/${it.id}")
//                    }
//                }
                items(searchPhotos.itemCount) { index ->
                    searchPhotos[index]?.let {
                        ImageItem(photosItem = it) {
                            navController.navigate(NavigationRoute.ImageDetails.route + "/${it.id}")
                        }
                    }
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LottieAnimation(
                    composition = composition.value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(300.dp)
                )
                Text(
                    text = "NO RESULTS FOUND",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    focus: MutableState<Boolean>,
    onSearch: (query: String) -> Unit,
) {
    TextField(
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            disabledLabelColor = lightBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = "Search here")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            .onFocusChanged {
                focus.value = it.hasFocus
            }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ), keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchQuery.value)
            }
        ),
        shape = RoundedCornerShape(30.dp),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        },
        singleLine = true
    )

}


