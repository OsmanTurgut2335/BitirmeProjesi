package com.osman.bitirmeprojesi.views.customviews

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.osman.bitirmeprojesi.R

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier,
          //resource ID of the Lottie animation
                      @RawRes animationResId: Int ) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationResId)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}