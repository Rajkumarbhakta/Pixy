package com.rkbapps.pixy.categories.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rkbapps.pixy.categories.models.Topic
import com.rkbapps.pixy.categories.viewmodels.CategoriesViewModel
import com.rkbapps.pixy.navigation.NavigationRoute
import com.rkbapps.pixy.screens.NoInternet
import com.rkbapps.pixy.utils.carouselTransition
import com.rkbapps.pixy.utils.checkNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(navController: NavHostController) {
    if (LocalContext.current.checkNetwork()) {
        val viewModel: CategoriesViewModel = hiltViewModel()
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(8.dp)
            ) {
                val topic: State<List<Topic>> = viewModel.topics.collectAsState()
                TextTittle(title = "TRENDING TOPICS")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    CarouselTopic(topics = topic) { it ->
                        navController.navigate(route = NavigationRoute.TopicDetails.route + "/${it.id}/${it.title}")
                    }
                }
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.5f)
//                    .padding(8.dp)
//            ) {
//                val collection: State<List<Topic>> = viewModel.collection.collectAsState()
//                TextTittle(title = "TRENDING COLLECTION")
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 8.dp)
//                ) {
//                    CarouselTopic(topics = collection) {
//                        navController.navigate(route = NavigationRoute.CollectionDetails.route + "/${it.id}/${it.title}")
//                    }
//                }
//
//            }

        }
    } else {
        NoInternet()
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CarouselTopic(topics: State<List<Topic>>, onItemClicked: (topic: Topic) -> Unit) {
    val pageCount = topics.value.size
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by rememberSaveable {
                    mutableIntStateOf(0)
                }
                LaunchedEffect(key1 = currentPageKey) {
                    launch {
                        delay(timeMillis = 5000)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(page = nextPage)
                        currentPageKey = nextPage
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            HorizontalPager(
                modifier = Modifier,
                state = pagerState,
                pageSpacing = 16.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(
                    horizontal = 32.dp
                ),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fill,
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                key = null,
                pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                    Orientation.Horizontal
                ),
                pageContent =  {page->
                    val item: Topic = topics.value[page]
                    item.let {
                        ElevatedCard(
                            elevation = CardDefaults.elevatedCardElevation(10.dp),
                            onClick = { onItemClicked(it) },
                            modifier = Modifier
                                .carouselTransition(page, pagerState)
                        ) {
                            CarouselItem(it)
                        }
                    }
                }
            )
        }
//        if (carouselLabel.isNotBlank()) {
//            Text(text = carouselLabel, style = MaterialTheme.typography.labelSmall)
//        }
    }
}

@Composable
fun CarouselItem(item: Topic) {
    Box {
        AsyncImage(
            model = item.cover_photo?.urls?.small,
            contentDescription = item.cover_photo?.description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
            //.height(180.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.Gray,
                            Color(0xFFFEFEFA)

                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.total_photos.toString(),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TextTittle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //TextTittle(title = "TRENDING COLLECTION")
}