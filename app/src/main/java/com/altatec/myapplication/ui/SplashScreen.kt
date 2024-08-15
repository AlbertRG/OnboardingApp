package com.altatec.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToLogin:() -> Unit) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.android_splash))

    LaunchedEffect(key1 = true){
        delay(3000)
        navigateToLogin()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(composition = composition)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashPrev() {
    AppTheme {
        SplashScreen {}
    }
}