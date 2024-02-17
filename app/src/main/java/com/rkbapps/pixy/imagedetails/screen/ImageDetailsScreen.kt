package com.rkbapps.pixy.imagedetails.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.rkbapps.pixy.R
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.imagedetails.viewmodel.ImageDetailsViewModel
import com.rkbapps.pixy.utils.getColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageDetailsScreen() {
    val viewModel: ImageDetailsViewModel = hiltViewModel()
    val photo: State<Photo> = viewModel.photo.collectAsState()
    val isFav: State<Boolean> = viewModel.isFav.collectAsState()
    val context = LocalContext.current

    val downloadStatus = remember {
        viewModel.downloadStatus
    }

    if (photo.value.urls.full.isNotEmpty()) {
        Scaffold(topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Preview",
                    fontFamily = FontFamily(Font(resId = R.font.softers_regular)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }, bottomBar = {
            BottomBar(item = photo.value)
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
//                AsyncImage(
//                    model = photo.value.urls.regular,
//                    contentDescription = photo.value.alt_description,
//                    //placeholder = painterResource(id = R.drawable.placeholder),
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    //contentScale = ContentScale.Inside,
//                    onLoading = {
////                       CircularProgressIndicator( strokeWidth = 2.dp )
//                    }
//                )

                GlideImage(
                    model = photo.value.urls.regular,
                    contentDescription = photo.value.alt_description,
                    loading = placeholder(painter = painterResource(id = R.drawable.placeholder)),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    transition = CrossFade,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ActionIcons(
                        icon = R.drawable.heartlovelike,
                        tint = if (isFav.value) Color.Red else MaterialTheme.colorScheme.onSecondary,
                        title = "Likes",
                        count = photo.value.likes
                    ) {
                        if (isFav.value) {
                            viewModel.removeFromFav(photo.value.id)
                        } else {
                            viewModel.addToFav(photo.value)
                        }
                        viewModel.changeIsFav()
                    }

                    ActionIcons(
                        icon = R.drawable.download,
                        title = "Download",
                        count = photo.value.downloads
                    ) {
//                        context.downloadService(
//                            photo.value.alt_description ?: "pixy image",
//                            photo.value.urls.raw
//                        )

                        viewModel.trackDownload(context, photo.value)
                        Toast.makeText(context, downloadStatus.value, Toast.LENGTH_SHORT).show()

                    }
                }

            }

        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun BottomBar(item: Photo) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        getColor(item.color),
                        Color.Transparent
                    )
                )
            )
            .padding(6.dp)
    ) {
        AsyncImage(
            model = item.user.profile_image.medium,
            contentDescription = "profile icon",
            modifier = Modifier
                .height(70.dp)
                .padding(10.dp)
                .clip(shape = CircleShape)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(item.user.links.html)
                    context.startActivity(intent)
                },
        )
        Column {
            Text(
                text = item.user.name,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "@${item.user.username}",
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@Composable
fun ActionIcons(
    icon: Int,
    tint: Color = Color.White,
    title: String,
    count: Int,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .fillMaxWidth()
                .clip(shape = CircleShape)
                .background(color = Color(0x501E3187))
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = title,
                tint = tint
            )
        }
        Text(text = "$count")
    }
}




