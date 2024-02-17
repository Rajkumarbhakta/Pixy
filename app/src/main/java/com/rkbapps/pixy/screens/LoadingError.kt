package com.rkbapps.pixy.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun LoadingError() {
    val composition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        LottieAnimation(
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height(300.dp)
        )
    }

}