package com.rkbapps.pixy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rkbapps.pixy.R

@Composable
fun NoInternet() {
    val composition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_internet))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height(300.dp)
        )
        Text(
            text = "NO INTERNET",
            style = MaterialTheme.typography.titleLarge
        )
    }
}