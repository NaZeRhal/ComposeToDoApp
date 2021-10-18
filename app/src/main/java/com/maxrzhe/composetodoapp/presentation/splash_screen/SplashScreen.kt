package com.maxrzhe.composetodoapp.presentation.splash_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.core.util.Constants.SPLASH_SCREEN_DELAY
import com.maxrzhe.composetodoapp.presentation.ui.theme.ComposeToDoAppTheme
import com.maxrzhe.composetodoapp.presentation.ui.theme.SPLASH_SCREEN_LOGO_SIZE
import com.maxrzhe.composetodoapp.presentation.ui.theme.splashScreenBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {

    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else SPLASH_SCREEN_LOGO_SIZE,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.splashScreenBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.todo_logo),
            contentDescription = stringResource(R.string.splash_logo),
            modifier = Modifier
                .size(SPLASH_SCREEN_LOGO_SIZE)
                .alpha(alpha = alphaState)
                .offset(y = offsetState)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreview() {
    Column() {
        SplashScreen(navigateToListScreen = {})
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreviewDark() {
    ComposeToDoAppTheme(darkTheme = true) {
        Column() {
            SplashScreen(navigateToListScreen = {})
        }
    }
}