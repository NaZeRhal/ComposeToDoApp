package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val router = remember(navController) {
        Router(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        splashScreenComposable(navigateToListScreen = router.fromSplashToList)
        taskListComposable(navigateToDetailScreen = router.fromListToDetail)
        taskDetailComposable(navigateToListScreen = router.fromDetailToList)
    }
}